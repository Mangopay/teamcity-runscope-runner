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

    public RunscopeRunWatcher(final RunscopeClient client, final Run run, final BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;

        steps = client.getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        stepsStatus = new RequestStatus[steps.size() + 1];
    }

    public TestResult watch() throws InterruptedException, CancellationException {
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
        if(stepIndex > steps.size()) return;
        logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void logStepFinished(final int stepIndex, final Request request) {
        RequestStatus result = request.getResult();
        String testName = getStepTestName(stepIndex);

        if(result == RequestStatus.FAILED) {
            logger.logTestFailed(testName, "Failed", run.getUrl());
        }
        else if(result == RequestStatus.CANCELED) {
            logger.logTestFailed(testName, "Canceled", run.getUrl());
        }

        logger.logTestFinished(testName);
    }

    private String getStepTestName(final int stepIndex) {
        StringBuilder sb = new StringBuilder();
        if(stepIndex > 0) {
            sb.append(stepIndex);
            sb.append(" - ");
        }

        if(stepIndex == 0) sb.append("Initial script");
        else {
            Step step = steps.get(stepIndex -1);
            if("pause".equals(step.getStepType())) {
                sb.append("Pause ");
                sb.append(step.getDuration());
                sb.append(" second(s)");
            }
            else if("ghost-inspector".equals(step.getStepType())) {
                sb.append("[Ghost Inspector] ");
                sb.append(step.getTestName());
            }
            else {
                sb.append(step.getNote());
            }
        }

        return sb.toString();
    }
}
