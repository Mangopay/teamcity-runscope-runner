package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.List;
import java.util.Vector;

public class RunscopeTestSet {
    private String bucketId;
    private String testId;
    private String environment;
    private RunscopeClient client;
    private BuildProgressLogger logger;

    public RunscopeTestSet(String token, String bucketId, String testId, String environment, BuildProgressLogger logger) {
        this.client = new RunscopeClient(token);

        this.bucketId = bucketId;
        this.testId = testId;
        this.environment = environment;

        this.logger = logger;
    }

    public void run() throws RunBuildException {
        Bucket bucket = getBucket();
        List<Test> tests = getTests();

        for(Test test : tests) {
            Trigger trigger = trigger(test);
            for(Run run : trigger.getRuns()) {
                RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, this.logger);
                TestResult result = watcher.watch();
                logResult(test, result);
            }
        }
    }

    private Bucket getBucket() throws RunBuildException {
        Bucket bucket = client.getBucket(this.bucketId);
        if(bucket == null) {
            throw new RunBuildException("Cannot retrieve bucket " + this.bucketId);
        }

        return bucket;
    }

    private List<Test> getTests() throws RunBuildException {
        if(this.testId == null || this.testId.isEmpty()) {
            return client.getBucketTests(this.bucketId);
        }

        List<Test> tests = new Vector<Test>();
        Test test = client.getTest(this.bucketId, this.testId);
        if(test == null) {
            throw new RunBuildException("Cannot retrieve test " + this.testId);
        }

        tests.add(test);
        return tests;
    }

    private Trigger trigger(Test test) {
        Trigger trigger = client.trigger(test);
        logger.logTestStarted(test.getName());

        return trigger;
    }

    private void logResult(Test test, TestResult result) {
        String status = result.getResult();
        if("failed".equals(status)) {
            logger.logTestFailed(test.getName(), "test has failed", "no details");
        }
        else if("canceled".equals(status)) {
            logger.logTestFailed(test.getName(), "test has been canceled", "no details");
        }

        logger.logTestFinished(test.getName());
    }
}
