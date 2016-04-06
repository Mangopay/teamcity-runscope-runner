package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.FlowLogger;
import jetbrains.buildServer.messages.BuildMessage1;

import java.util.Date;

public class TeamcitySimulation {

    //simulate workflow that teamcity should implement on a test run
    public static void main(String[] args) {
        String token = args[0];
        String bucket = args[1];
        String test =  args.length > 2 ? args[2] : "";

        RunscopeClient client = new RunscopeClient(token);
        RunscopeTestSet runscopeTestSet = new RunscopeTestSet(token, bucket, test, "", new SystemOutLogger());

        try {
            runscopeTestSet.run();
        } catch(RunBuildException ex) {
            System.err.print(ex);
        }
    }
}

class SystemOutLogger implements BuildProgressLogger {

    private void log(String cateory, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(cateory);
        for(Object arg : args) {
            sb.append("\n\t");
            sb.append(arg);
        }

        System.out.print(sb.toString());
    }
    @Override
    public void activityStarted(String s, String s1) {
        log("activityStarted", s, s1);
    }

    @Override
    public void activityStarted(String s, String s1, String s2) {
        log("activityStarted", s, s1, s2);
    }

    @Override
    public void activityFinished(String s, String s1) {
        log("activityFinished", s, s1);
    }

    @Override
    public void targetStarted(String s) {
        log("targetStarted", s);

    }

    @Override
    public void targetFinished(String s) {
        log("targetFinished", s);
    }

    @Override
    public void buildFailureDescription(String s) {
        log("buildFailureDescription", s);
    }

    @Override
    public void internalError(String s, String s1, Throwable throwable) {
        log("internalError", s, s1, throwable);
    }

    @Override
    public void progressStarted(String s) {
        log("progressStarted", s);
    }

    @Override
    public void progressFinished() {
        log("progressFinished");
    }

    @Override
    public void logMessage(BuildMessage1 buildMessage1) {
        log("logMessage", buildMessage1);
    }

    @Override
    public void logTestStarted(String s) {
        log("logTestStarted", s);
    }

    @Override
    public void logTestStarted(String s, Date date) {
        log("logTestStarted", s, date);
    }

    @Override
    public void logTestFinished(String s) {
        log("logTestFinished", s);
    }

    @Override
    public void logTestFinished(String s, Date date) {
        log("logTestFinished", s, date);
    }

    @Override
    public void logTestIgnored(String s, String s1) {
        log("logTestIgnored", s, s1);
    }

    @Override
    public void logSuiteStarted(String s) {
        log("logSuiteStarted", s);
    }

    @Override
    public void logSuiteStarted(String s, Date date) {
        log("logSuiteStarted", s, date);
    }

    @Override
    public void logSuiteFinished(String s) {
        log("logSuiteFinished", s);
    }

    @Override
    public void logSuiteFinished(String s, Date date) {
        log("logSuiteFinished", s, date);
    }

    @Override
    public void logTestStdOut(String s, String s1) {
        log("logTestStdOut", s, s1);
    }

    @Override
    public void logTestStdErr(String s, String s1) {
        log("logTestStdErr", s, s1);
    }

    @Override
    public void logTestFailed(String s, Throwable throwable) {
        log("logTestFailed", s, throwable);
    }

    @Override
    public void logComparisonFailure(String s, Throwable throwable, String s1, String s2) {
        log("logComparisonFailure", s, throwable, s1, s2);
    }

    @Override
    public void logTestFailed(String s, String s1, String s2) {
        log("logTestFailed", s, s1, s2);
    }

    @Override
    public void flush() {

    }

    @Override
    public void ignoreServiceMessages(Runnable runnable) {

    }

    @Override
    public FlowLogger getFlowLogger(String s) {
        return null;
    }

    @Override
    public FlowLogger getThreadLogger() {
        return null;
    }

    @Override
    public String getFlowId() {
        return null;
    }

    @Override
    public void logBuildProblem(BuildProblemData buildProblemData) {
        log("logBuildProblem", buildProblemData);
    }

    @Override
    public void message(String s) {
        log("message", s);
    }

    @Override
    public void error(String s) {
        log("error", s);
    }

    @Override
    public void warning(String s) {
        log("warning", s);
    }

    @Override
    public void exception(Throwable throwable) {
        log("exception", throwable);
    }

    @Override
    public void progressMessage(String s) {
        log("progressMessage", s);
    }
}
