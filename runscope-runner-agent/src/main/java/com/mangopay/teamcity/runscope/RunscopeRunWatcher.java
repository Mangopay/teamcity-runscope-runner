package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.List;
import java.util.concurrent.CancellationException;

public class RunscopeRunWatcher {
    private RunscopeClient client;
    private Run run;
    private List<Step> steps;

    private BuildProgressLogger logger;
    private RequestStatus[] stepsStatus;

    public RunscopeRunWatcher(RunscopeClient client, Run run, BuildProgressLogger logger) {
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
            result = client.getRunResult(this.run);
            logProgress(result);
            done = result.getResult().isDone();
        }

        if(result.getResult() == TestStatus.CANCELED) throw new CancellationException("Test has been canceled on Runscope side");
        return result;
    }

    private void logProgress(TestResult result) {
        List<Request> requests = result.getRequests();

        for(int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            RequestStatus status = request.getResult();

            if(status == null) continue;
            else if(status.equals(this.stepsStatus[i])) continue;

            if(status.isDone()) {
                logStepFinished(i, request);
                logStepStarted(i + 1);
            }

            this.stepsStatus[i] = status;
        }
    }
    private void logStepStarted(int stepIndex) {
        if(stepIndex > this.steps.size()) return;
        this.logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void logStepFinished(int stepIndex, Request request) {
        RequestStatus result = request.getResult();
        String testName = getStepTestName(stepIndex);

        if(result == RequestStatus.FAILED) {
            this.logger.logTestFailed(testName, "Failed", this.run.getUrl());
        }
        else if(result == RequestStatus.CANCELED) {
            this.logger.logTestFailed(testName, "Canceled", this.run.getUrl());
        }

        this.logger.logTestFinished(testName);
    }

    private String getStepTestName(int stepIndex) {
        StringBuilder sb = new StringBuilder();
        if(stepIndex > 0) {
            sb.append(stepIndex);
            sb.append(" - ");
        }

        if(stepIndex == 0) sb.append("Initial script");
        else {
            Step step = this.steps.get(stepIndex -1);
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
