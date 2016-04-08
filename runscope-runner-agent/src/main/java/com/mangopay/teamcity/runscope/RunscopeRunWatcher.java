package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.List;
import java.util.concurrent.CancellationException;

public class RunscopeRunWatcher {
    private final RunscopeClient client;
    private final Run run;
    private final List<Step> steps;

    private final BuildProgressLogger logger;
    private final RequestStatus[] stepsStatus;

    private final RequestFormatter formatter;

    public RunscopeRunWatcher(final RunscopeClient client, final Run run, final BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;

        Step initialStep = new Step();
        initialStep.setNote("Initial script");
        steps = client.getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        steps.add(0, initialStep);
        stepsStatus = new RequestStatus[steps.size()];

        formatter = new RequestFormatter(run);
    }

    public TestResult watch() throws InterruptedException, CancellationException {
        logStepStarted(0);
        Boolean done = false;
        TestResult result = null;

        while(!done) {
            Thread.sleep(1000);
            result = client.getRunResult(run);
            logProgress(result);
            done = result.getResult().isDone();
        }

        if(result.getResult() == TestStatus.CANCELED) throw new CancellationException("Test has been canceled on Runscope side");
        return result;
    }

    private void logProgress(final TestResult result) {
        List<Request> requests = result.getRequests();

        for(int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            RequestStatus status = request.getResult();

            if(status == null) continue;
            else if(status.equals(stepsStatus[i])) continue;

            if(status.isDone()) {
                logStepFinished(i, request);
                logStepStarted(i + 1);
            }

            stepsStatus[i] = status;
        }
    }
    private void logStepStarted(final int stepIndex) {
        if(stepIndex > steps.size() - 1) return;
        logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void logStepFinished(final int stepIndex, final Request request) {
        RequestStatus result = request.getResult();
        Step step = steps.get(stepIndex);

        String testName = getStepTestName(stepIndex);
        String output = formatter.getOutput(step, request);

        if(result == RequestStatus.FAILED) {
            logger.logTestFailed(testName, "Failed", output);
        }
        else if(result == RequestStatus.CANCELED) {
            logger.logTestFailed(testName, "Canceled", output);
        }
        else logger.message(output);

        logger.logTestFinished(testName);
    }

    private String getStepTestName(final int stepIndex) {
        StringBuilder sb = new StringBuilder();
        if(stepIndex > 0) {
            sb.append(stepIndex);
            sb.append(" - ");
        }

        Step step = steps.get(stepIndex);
        return sb.append(formatter.getName(step)).toString();
    }
}
