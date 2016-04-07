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
    private String[] stepsStatus;

    public RunscopeRunWatcher(RunscopeClient client, Run run, BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;

        steps = client.getTestSteps(this.run.getBucketKey(), this.run.getTestId());
        stepsStatus = new String[steps.size() + 1];
    }

    public TestResult watch() throws InterruptedException, CancellationException {
        Boolean done = false;
        TestResult result = null;

        while(!done) {
            Thread.sleep(1000);
            result = client.getRunResult(this.run);
            logProgress(result);
            done = isDone(result);
        }

        if(isCanceled(result)) throw new CancellationException("Test has been canceled on Runscope side");
        return result;
    }

    private void logProgress(TestResult result) {
        List<Request> requests = result.getRequests();

        for(int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            String status = request.getResult();

            if(status == null) continue;
            else if(status.equals(this.stepsStatus[i])) continue;

            if (this.stepsStatus[i] == null) logStepStarted(i);
            if(isDone(status)) logStepFinished(i, request);

            this.stepsStatus[i] = status;
        }
    }

    private Boolean isDone(TestResult result) {
        String status = result.getResult();
        return ("pass".equals(status) || "fail".equals(status) || isCanceled(result));
    }

    private Boolean isCanceled(TestResult result) {
        String status = result.getResult();
        return "canceled".equals(status);
    }

    private Boolean isDone(String result) {
        return ("pass".equals(result) || "fail".equals(result) || isCanceled(result));
    }

    private Boolean isCanceled(String result) {
        return "canceled".equals(result);
    }

    private void logStepStarted(int stepIndex) {
        this.logger.logTestStarted(getStepTestName(stepIndex));
    }

    private void logStepFinished(int stepIndex, Request request) {
        String result = request.getResult();
        String testName = getStepTestName(stepIndex);

        if("fail".equals(result)) {
            this.logger.logTestFailed(testName, "Failed", "No details available for now");
        }
        else if("canceled".equals(result)) {
            this.logger.logTestFailed(testName, "Canceled", "Canceled on Runscope side");
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
