package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Bucket;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.FlowLogger;
import jetbrains.buildServer.util.StringUtil;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class RunscopeTestSetRunner implements Callable {

    private final RunscopeClient client;
    private final String bucketId;
    private final String testsId;
    private final String environment;
    private final FlowLogger logger;

    public RunscopeTestSetRunner(final String token, final String bucketId, final String testsId, final String environment, final BuildProgressLogger logger) {
        this.client = new RunscopeClient(token);
        this.bucketId = bucketId;
        this.testsId = testsId;
        this.environment = environment;
        this.logger = logger.getFlowLogger(this.bucketId);
    }
    @Override
    public Object call() throws RunBuildException {
        logStarted();

        Bucket bucket = getBucket();
        List<Test> tests = getTests();

        logger.logSuiteStarted(bucket.getName());

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<TestResult> completionService = new ExecutorCompletionService<TestResult>(threadPool);

        for(Test test : tests) {
            RunscopeTestRunner runner = new RunscopeTestRunner(client, test, environment, logger);
            completionService.submit(runner);
        }

        for(int i = 0; i < tests.size(); i++) {
            try {
                Future<TestResult> resultFuture = completionService.take();
            } catch (InterruptedException e) {
                logger.exception(e);
            }
        }

        threadPool.shutdown();
        logger.logSuiteFinished(bucket.getName());
        return null;
    }

    private void logStarted() {
        logger.message("Running Runscope tests");
        logger.message("Bucket : " + bucketId);
        logger.message("Environment : " + environment);
        logger.message("Tests : " + testsId);
    }

    private Bucket getBucket() throws RunBuildException {
        Bucket bucket = client.getBucket(bucketId);
        if(bucket == null) {
            throw new RunBuildException("Cannot retrieve bucket " + bucketId);
        }

        return bucket;
    }

    private List<Test> getTests() throws RunBuildException {
        if(StringUtil.isEmptyOrSpaces(testsId)) {
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
}
