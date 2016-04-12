package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.concurrent.CancellationException;

class RunscopeRunWatcher {
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
        requestLogger = new RequestLogger(run, logger);

        final Step initialStep = new Step();
        initialStep.setNote("Initial script");
        steps = client.getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        steps.add(0, initialStep);
        stepsStatus = new RequestStatus[steps.size()];
    }

    public TestResult watch() throws InterruptedException, CancellationException, RunBuildException {
        logStepStarted(0);
        Boolean done = false;
        TestResult result = null;
        int errorsInARow = 0;

        while(!done) {
            Thread.sleep(1000);
            try {
                result = client.getRunResult(run);
                logProgress(result);
                done = result.getResult().isDone();

                errorsInARow = 0;
            }
            catch(NotFoundException ex) {
                errorsInARow++;
            }
            catch(InternalServerErrorException ex) {
                errorsInARow++;
            }
            finally {
                if(errorsInARow > 10) {
                    throw new RunBuildException("Maximum errors in a row reached, aborting build !");
                }
            }
        }

        if(result.getResult() == TestStatus.CANCELED) throw new CancellationException("Test has been canceled on Runscope side");
        return result;
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
        final StringBuilder sb = new StringBuilder();
        if(stepIndex > 0) {
            sb.append(stepIndex);
            sb.append(" - ");
        }

        Step step = steps.get(stepIndex);
        return sb.append(requestLogger.getName(step)).toString();
    }
}
