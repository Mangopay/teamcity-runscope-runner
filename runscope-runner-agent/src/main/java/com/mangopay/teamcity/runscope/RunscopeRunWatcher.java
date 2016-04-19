package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.concurrent.Callable;

class RunscopeRunWatcher implements Callable<WatchResult> {

    private final RunscopeClient client;
    private final Run run;
    private final BuildProgressLogger logger;
    private final RequestLogger requestLogger;
    private List<Step> steps;

    private int started = -1;
    private int finished = -1;

    public RunscopeRunWatcher(final RunscopeClient client, final Run run, final BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;
        requestLogger = new RequestLogger(this.run, this.logger);
    }

    @Override
    public WatchResult call() throws InterruptedException, RunBuildException {
        final WatchResult result = new WatchResult();
        initSteps();

        logger.message(String.format(RunscopeConstants.LOG_SEE_FULL_LOG, run.getUrl()));
        boolean done = false;
        Integer errorsInARow = 0;

        do {
            Thread.sleep(1000);
            try {
                done = update(result);
                errorsInARow = 0;
            } catch (final NotFoundException ex) {
                errorsInARow = throwIfNeeded(errorsInARow, ex);
            } catch (final InternalServerErrorException ex) {
                errorsInARow = throwIfNeeded(errorsInARow, ex);
            }
        }
        while (!done);

        return result;
    }

    private void initSteps() {
        final List<Step> result = new ArrayList<Step>();
        final List<Step> steps = client.getTestSteps(run.getBucketKey(), run.getTestId());

        final Step initialStep = new Step();
        initialStep.setNote("Initial script");
        initialStep.setId(UUID.randomUUID().toString());
        result.add(0, initialStep);

        for (final Step step : steps) {
            addSteps(step, result);
        }

        this.steps = result;
    }

    private void addSteps(final Step step, final List<Step> list) {
        list.add(step);

        if (step.getSteps() == null || step.getSteps().isEmpty()) return;

        for (final Step s : step.getSteps()) {
            addSteps(s, list);
        }

        if (step.getStepType() == StepType.CONDITION) {
            //condition step adds another request at the end. Adding a fake step to match it.
            Step fakeStep = new Step();
            list.add(fakeStep);
        }
    }

    private int throwIfNeeded(Integer errorsInARow, final Exception ex) throws RunBuildException {
        if (errorsInARow > 10) throw new RunBuildException("Maximum retries exceeded", ex);
        return errorsInARow + 1;
    }

    private boolean update(WatchResult result) {
        final TestResult testResult = client.getRunResult(run);
        final List<Request> requests = testResult.getRequests();

        int finished = -1;
        int started = 0;

        for (int i = 0; i < requests.size(); i++) {
            final Request request = requests.get(i);
            final RequestStatus requestResult = request.getResult();

            if (requestResult == null) continue;
            replaceProperties(i, request);

            finished = i;
            started = i + 1;
        }

        final int lower = Math.min(this.started + 1, this.finished + 1);
        final int upper = Math.max(started + 1, finished + 1);
        for (int i = lower; i < upper; i++) {
            if (i > this.started && i <= started) stepStarted(i);
            if (i > this.finished && i <= finished) stepFinished(i, requests.get(i), result);
        }

        this.finished = finished;
        this.started = started;

        result.setTestResult(testResult);
        return testResult.getResult().isDone();
    }
    private void replaceProperties(final int stepIndex, final Request request) {
        if (request.getAssertions() == null) return;

        final Step step = steps.get(stepIndex);
        int i = 0;

        for (final RequestAssertion assertion : request.getAssertions()) {
            if (i >= step.getAssertions().size()) break;

            final String stepProperty = step.getAssertions().get(i).getProperty();
            if (StringUtil.isEmptyOrSpaces(assertion.getProperty()) && !StringUtil.isEmptyOrSpaces(stepProperty)) {
                assertion.setProperty(stepProperty);
            }

            i++;
        }
    }

    private void stepStarted(final int stepIndex) {
        if (stepIndex > steps.size() - 1) return;
        logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void stepFinished(final int stepIndex, final Request request, final WatchResult result) {
        final RequestStatus requestResult = request.getResult();
        final Step step = steps.get(stepIndex);

        final String testName = getStepTestName(stepIndex);
        final String output = requestLogger.log(step, request);
        setBuildParameters(request, result);

        if (requestResult == RequestStatus.FAILED) {
            logger.logTestFailed(testName, "Failed", output);
        } else if (requestResult == RequestStatus.CANCELED) {
            logger.logTestFailed(testName, "Canceled", output);
        }

        logger.logTestFinished(testName);
    }

    private void setBuildParameters(Request request, WatchResult result) {
        List<RequestVariable> variables = request.getVariables();

        for(final RequestVariable variable : variables) {
            if(variable.getResult() != BinaryStatus.PASSED) continue;
            result.putVariable(variable.getName(), variable.getValue());
        }
    }

    private String getStepTestName(final int stepIndex) {
        final String format = "%d - %s";
        final Step step = steps.get(stepIndex);

        return String.format(format, stepIndex, requestLogger.getName(step));
    }
}
