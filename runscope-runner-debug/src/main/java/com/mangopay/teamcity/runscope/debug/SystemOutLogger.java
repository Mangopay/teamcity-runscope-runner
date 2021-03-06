package com.mangopay.teamcity.runscope.debug;

import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.agent.FlowLogger;
import jetbrains.buildServer.messages.BuildMessage1;

import java.util.Date;

class SystemOutLogger implements FlowLogger {

    private String flow;
    private static int index;

    public SystemOutLogger() {

    }

    public SystemOutLogger(String flow) {
        if(flow != null && !flow.isEmpty()) {
            this.flow = '[' + flow + ']';
        }
    }

    private void log(String cateory, Object... args) {
        StringBuilder sb = new StringBuilder();
        if(flow != null && !flow.isEmpty()) sb.append(flow);

        sb.append(cateory);
        for(Object arg : args) {
            sb.append("\n\t");
            sb.append(arg);
        }

        System.out.println(sb.toString());
    }

    public void activityStarted(String s, String s1) {
        log("activityStarted", s, s1);
    }


    public void activityStarted(String s, String s1, String s2) {
        log("activityStarted", s, s1, s2);
    }


    public void activityFinished(String s, String s1) {
        log("activityFinished", s, s1);
    }


    public void targetStarted(String s) {
        log("targetStarted", s);

    }


    public void targetFinished(String s) {
        log("targetFinished", s);
    }


    public void buildFailureDescription(String s) {
        log("buildFailureDescription", s);
    }


    public void internalError(String s, String s1, Throwable throwable) {
        log("internalError", s, s1, throwable);
    }


    public void progressStarted(String s) {
        log("progressStarted", s);
    }


    public void progressFinished() {
        log("progressFinished");
    }

    @Override
    public void logMessage(BuildMessage1 buildMessage1) {
        log("logMessage", buildMessage1);
    }


    public void logTestStarted(String s) {
        log("logTestStarted", s);
    }


    public void logTestStarted(String s, Date date) {
        log("logTestStarted", s, date);
    }


    public void logTestFinished(String s) {
        log("logTestFinished", s);
    }


    public void logTestFinished(String s, Date date) {
        log("logTestFinished", s, date);
    }


    public void logTestIgnored(String s, String s1) {
        log("logTestIgnored", s, s1);
    }


    public void logSuiteStarted(String s) {
        log("logSuiteStarted", s);
    }


    public void logSuiteStarted(String s, Date date) {
        log("logSuiteStarted", s, date);
    }


    public void logSuiteFinished(String s) {
        log("logSuiteFinished", s);
    }


    public void logSuiteFinished(String s, Date date) {
        log("logSuiteFinished", s, date);
    }


    public void logTestStdOut(String s, String s1) {
        log("logTestStdOut", s, s1);
    }


    public void logTestStdErr(String s, String s1) {
        log("logTestStdErr", s, s1);
    }


    public void logTestFailed(String s, Throwable throwable) {
        log("logTestFailed", s, throwable);
    }


    public void logComparisonFailure(String s, Throwable throwable, String s1, String s2) {
        log("logComparisonFailure", s, throwable, s1, s2);
    }


    public void logTestFailed(String s, String s1, String s2) {
        log("logTestFailed", s, s1, s2);
    }


    public void flush() {

    }


    public void ignoreServiceMessages(Runnable runnable) {

    }

    @Override
    public FlowLogger getFlowLogger(String s) {
        Integer thisIndex = index++;
        return new SystemOutLogger(thisIndex.toString());


        //return new SystemOutLogger(s);

    }

    @Override
    public FlowLogger getThreadLogger() {
        return this;
    }

    public String getFlowId() {
        return null;
    }

    @Override
    public void logBuildProblem(BuildProblemData buildProblemData) {

    }

    public void message(String s) {
        log("message", s);
    }


    public void error(String s) {
        log("error", s);
    }


    public void warning(String s) {
        log("warning", s);
    }


    public void exception(Throwable throwable) {
        log("exception", throwable);
    }


    public void progressMessage(String s) {
        log("progressMessage", s);
    }

    @Override
    public void startFlow() {
        log("startFlow");
    }

    @Override
    public void disposeFlow() {
        log("disposeFlow");
    }
}
