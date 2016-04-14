package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Callable;

class RunscopeRunWatcher implements Callable<TestResult> {
    private final RunscopeClient client;
    private final Run run;
    private final List<Step> steps;

    private final BuildProgressLogger logger;
    private final RequestStatus[] stepsStatus;

    private final RequestLogger requestLogger;

    public RunscopeRunWatcher(final RunscopeClient client, final Run run, final BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;
        requestLogger = new RequestLogger(this.run, this.logger);

        steps = getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        insertInitialStep();
        stepsStatus = new RequestStatus[steps.size()];
    }

    private List<Step> getTestSteps(String bucketKey, String testId) {
        List<Step> steps = client.getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        List<Step> result = new Vector<Step>();

        for(Step step : steps) {
            addSteps(step, result);
        }
        return result;
    }

    private void addSteps(Step step, List<Step> list) {
        list.add(step);
        if(step.getSteps() == null || step.getSteps().size() == 0) return;

        for(Step s2 : step.getSteps()) {
            addSteps(s2, list);
        }

        if(step.getStepType() == StepType.CONDITION) {
            //condition step adds another request at the end. Adding a fake step to match it.
            Step fakeStep = new Step();
            list.add(step);
        }
    }

    private void insertInitialStep() {
        final Step initialStep = new Step();
        initialStep.setNote("Initial script");
        initialStep.setId(UUID.randomUUID().toString());
        steps.add(0, initialStep);
    }

    @Override
    public TestResult call() throws InterruptedException, RunBuildException {
        logStepStarted(0);
        Boolean done = false;
        TestResult result = null;
        Integer errorsInARow = 0;

        while(!done) {
            Thread.sleep(1000);
            try {
                result = client.getRunResult(run);
                logProgress(result);
                done = result.getResult().isDone();

                errorsInARow = 0;
            }
            catch(NotFoundException ex) {
                throwIfNeeded(errorsInARow, ex);
            }
            catch(InternalServerErrorException ex) {
                throwIfNeeded(errorsInARow, ex);
            }
        }

        return result;
    }

    private void throwIfNeeded(Integer errorsInARow, Exception ex) throws RunBuildException {
        errorsInARow++;
        if(errorsInARow > 10) throw new RunBuildException("Maximum retries exceeded", ex);
    }

    private void logProgress(final TestResult result) {
        final List<Request> requests = result.getRequests();

        for(int i = 0; i < requests.size(); i++) {
            final Request request = requests.get(i);
            final RequestStatus status = request.getResult();
            replaceProperties(request, i);

            if(status == null) continue;
            else if(status.equals(stepsStatus[i])) continue;

            logStepFinished(i, request);
            logStepStarted(i + 1);
            stepsStatus[i] = status;
        }
    }

    private void replaceProperties(final Request request, int stepIndex) {
        if(request.getAssertions() == null) return;

        Step step = steps.get(stepIndex);
        int i = 0;

        for(RequestAssertion assertion : request.getAssertions()) {
            if(i >= step.getAssertions().size()) break;

            String stepProperty = step.getAssertions().get(i).getProperty();
            if(StringUtil.isEmptyOrSpaces(assertion.getProperty()) && !StringUtil.isEmptyOrSpaces(stepProperty)) {
                assertion.setProperty(stepProperty);
            }

            i++;
        }
    }

    private void logStepStarted(final int stepIndex) {
        if(stepIndex > steps.size() - 1) return;
        logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void logStepFinished(final int stepIndex, final Request request) {
        final RequestStatus result = request.getResult();
        final Step step = steps.get(stepIndex);

        final String testName = getStepTestName(stepIndex);
        final String output = requestLogger.log(step, request);

        if(result == RequestStatus.FAILED) {
            logger.logTestFailed(testName, "Failed", output);
        }
        else if(result == RequestStatus.CANCELED) {
            logger.logTestFailed(testName, "Canceled", output);
        }

        logger.logTestFinished(testName);
    }

    private String getStepTestName(final int stepIndex) {
        String format = "%d - %s";
        final Step step = steps.get(stepIndex);

        return String.format(format, stepIndex, requestLogger.getName(step));
    }
}
