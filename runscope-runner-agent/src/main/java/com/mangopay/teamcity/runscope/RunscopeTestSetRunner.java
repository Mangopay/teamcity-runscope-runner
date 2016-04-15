package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Bucket;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.util.StringUtil;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RunscopeTestSetRunner implements Callable {

    private final RunscopeClient client;
    private final String bucketId;
    private final String testsId;
    private final String environment;
    private final BuildProgressLogger logger;

    public RunscopeTestSetRunner(final String token, final String bucketId, final String testsId, final String environment, final BuildProgressLogger logger) {
        this.client = new RunscopeClient(token);
        this.bucketId = bucketId;
        this.testsId = testsId;
        this.environment = environment;
        this.logger = logger;
    }

    @Override
    public Object call() throws RunBuildException {
        final Bucket bucket = getBucket();
        final List<Test> tests = getTests();

        logger.logSuiteStarted(bucket.getName());

        final ExecutorService threadPool = Executors.newFixedThreadPool(1);
        final CompletionService<TestResult> completionService = new ExecutorCompletionService<TestResult>(threadPool);

        for(Test test : tests) {
            final RunscopeTestRunner runner = new RunscopeTestRunner(client, test, environment, logger.getFlowLogger(test.getId()));
            completionService.submit(runner);
        }

       for(int i = 0; i < tests.size(); i++) {
            try {
                Future<TestResult> result = completionService.take();
                result.get();
            } catch (InterruptedException e) {
                logger.exception(e);
                logger.buildFailureDescription(e.getMessage());
            } catch(ExecutionException ex) {
                logger.exception(ex);
                Throwable cause = ex.getCause();
                logger.buildFailureDescription(cause.getMessage());
            }
        }

        threadPool.shutdown();
        logger.logSuiteFinished(bucket.getName());
        return null;
    }

    private Bucket getBucket() throws RunBuildException {
        try {
            return client.getBucket(bucketId);
        }
        catch(NotFoundException ex) {
            throw new RunBuildException("Cannot found bucket " + bucketId);
        }
    }

    private List<Test> getTests() throws RunBuildException {
        if(StringUtil.isEmptyOrSpaces(testsId)) {
            return client.getBucketTests(bucketId);
        }

        final List<Test> tests = new ArrayList<Test>();
        for(String testId : testsId.split("[\n, ]")) {
            try {
                Test test = client.getTest(bucketId, testId);
                tests.add(test);
            } catch (NotFoundException ex) {
                throw new RunBuildException("Cannot found test " + testId);
            }
        }

        return tests;
    }
}
