package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.FlowLogger;

import java.util.List;
import java.util.Vector;

class RunscopeTestSet {
    private final String bucketId;
    private final String testsId;
    private final String environment;
    private final RunscopeClient client;
    private final BuildProgressLogger logger;

    public RunscopeTestSet(final String token, final String bucketId, final String testsId, final String environment, final BuildProgressLogger logger) {
        this.client = new RunscopeClient(token);

        this.bucketId = bucketId;
        this.testsId = testsId;
        this.environment = environment;

        this.logger = logger.getFlowLogger(this.bucketId);
    }

    public void run() throws RunBuildException, InterruptedException {
        logRunStart();

        Bucket bucket = getBucket();
        List<Test> tests = getTests();

        logger.logSuiteStarted(bucket.getName());
        for(Test test : tests) {
            Trigger trigger = trigger(test, environment);

            for(Run run : trigger.getRuns()) {
                FlowLogger runLogger = logger.getFlowLogger(run.getTestRunId());
                RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, runLogger);
                watcher.watch();
                logTestFinished(test);
            }
        }
        logger.logSuiteFinished(bucket.getName());
    }

    private Bucket getBucket() throws RunBuildException {
        Bucket bucket = client.getBucket(bucketId);
        if(bucket == null) {
            throw new RunBuildException("Cannot retrieve bucket " + bucketId);
        }

        return bucket;
    }

    private List<Test> getTests() throws RunBuildException {
        if(testsId == null || testsId.isEmpty()) {
            return client.getBucketTests(bucketId);
        }

        List<Test> tests = new Vector<Test>();
        for(String testId : testsId.split("[\n,]")) {
            Test test = client.getTest(bucketId, testId);
            if (test == null) {
                throw new RunBuildException("Cannot retrieve test " + testId);
            }

            tests.add(test);
        }

        return tests;
    }

    private Trigger trigger(final Test test, final String environment) {
        Trigger trigger = client.trigger(test, environment);
        logger.logSuiteStarted(test.getName());

        return trigger;
    }

    private void logRunStart() {
        logger.message("Running Runscope tests");
        logger.message("Bucket : " + bucketId);
        logger.message("Environment : " + environment);
        logger.message("Tests : " + testsId);

    }

    private void logTestFinished(final Test test) {
        logger.logSuiteFinished(test.getName());
    }
}
