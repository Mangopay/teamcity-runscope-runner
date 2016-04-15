package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Bucket;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agentServer.Agent;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
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
        logger.activityStarted("Fetching data", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        final Bucket bucket = getBucket();
        final List<Test> tests = getTests();
        logger.activityFinished("Fetching data", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);

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
            Bucket bucket = client.getBucket(bucketId);
            logBucket(bucket);

            return bucket;
        }
        catch(NotFoundException ex) {
            throw new RunBuildException("Cannot find bucket " + bucketId);
        }
    }

    private List<Test> getTests() throws RunBuildException {
        final String[] testsId;
        final List<Test> tests = new ArrayList<Test>();

        if(StringUtil.isEmptyOrSpaces(this.testsId)) {
            logger.message("No test specified, finding bucket tests.");
            List<Test> bucketTests = client.getBucketTests(bucketId, 1000);
            testsId = new String[bucketTests.size()];

            for(int i = 0; i < bucketTests.size(); i++) {
                testsId[i] = bucketTests.get(i).getId();
            }
        }
        else {
            testsId = this.testsId.split(AgentConstants.TESTS_SPLIT);
        }

        logger.message(String.format("Fetching %d tests.", testsId.length));
        for (String testId : testsId) {
            try {
                Test test = client.getTest(bucketId, testId);
                tests.add(test);
            } catch (NotFoundException ex) {
                throw new RunBuildException("Cannot find test " + testId);
            }
        }

        logTests(tests);
        return tests;
    }

    private void logBucket(Bucket bucket) {
        logger.message(String.format("Fetched bucket %s : %s", bucketId, bucket.getName()));
    }

    private void logTests(List<Test> tests) {
        logger.activityStarted("Tests", String.format("%d test(s) found", tests.size()), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);

        for(Test test : tests) {
            logger.activityStarted(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
            if(!StringUtil.isEmptyOrSpaces(test.getDescription())) logger.message(test.getDescription());
            logger.message(String.format("Id : %s", test.getId()));
            logger.message(String.format("Steps : %d", test.getSteps().size()));
            logger.activityFinished(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        }

        logger.activityStarted("Tests toto", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
    }
}
