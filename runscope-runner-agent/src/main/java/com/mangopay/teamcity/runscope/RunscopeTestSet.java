package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.FlowLogger;

import java.util.List;
import java.util.Vector;

public class RunscopeTestSet {
    private final String bucketId;
    private final String testId;
    private final String environment;
    private final RunscopeClient client;
    private final BuildProgressLogger logger;

    public RunscopeTestSet(final String token, final String bucketId, final String testId, final String environment, final BuildProgressLogger logger) {
        this.client = new RunscopeClient(token);

        this.bucketId = bucketId;
        this.testId = testId;
        this.environment = environment;

        this.logger = logger.getFlowLogger(this.bucketId);
    }

    public void run() throws RunBuildException, InterruptedException {
        Bucket bucket = getBucket();
        List<Test> tests = getTests();

        logger.logSuiteStarted(bucket.getName());
        for(Test test : tests) {
            Trigger trigger = trigger(test);

            for(Run run : trigger.getRuns()) {
                FlowLogger runLogger = logger.getFlowLogger(run.getTestRunId());
                RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, runLogger);
                watcher.watch();
                logTestFinished(test);
            }
        }
        this.logger.logSuiteFinished(bucket.getName());
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

    private Trigger trigger(final Test test) {
        Trigger trigger = client.trigger(test);
        logger.logSuiteStarted(test.getName());

        return trigger;
    }

    private void logTestFinished(final Test test) {
        logger.logSuiteFinished(test.getName());
    }
}
