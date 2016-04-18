package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Bucket;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RunscopeTestSetRunner implements Callable {

    private final RunscopeClient client;
    private final BuildProgressLogger logger;
    private final RunscopeRunnerContext runscopeRunnerContext;
    private final BuildRunnerContext buildRunnerContext;

    public RunscopeTestSetRunner(@NotNull final BuildRunnerContext buildRunnerContext, @NotNull final RunscopeRunnerContext runscopeRunnerContext) {
        this.buildRunnerContext = buildRunnerContext;
        this.runscopeRunnerContext = runscopeRunnerContext;
        this.client = new RunscopeClient(this.runscopeRunnerContext.getToken());
        this.logger = this.runscopeRunnerContext.getLogger();
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
            final RunscopeTestRunner runner = new RunscopeTestRunner(buildRunnerContext, client, test, runscopeRunnerContext.getEnvironmentId(), runscopeRunnerContext.getInitialVariables(), logger.getFlowLogger(test.getId()));
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
            Bucket bucket = client.getBucket(runscopeRunnerContext.getBucketId());
            logBucket(bucket);

            return bucket;
        }
        catch(NotFoundException ex) {
            throw new RunBuildException("Cannot find bucket " + runscopeRunnerContext.getBucketId());
        }
    }

    private List<Test> getTests() throws RunBuildException {
        final List<Test> tests = new ArrayList<Test>();

        if(runscopeRunnerContext.getTestsIds().size() == 0) {
            logger.message("No test specified, finding bucket tests.");
            List<Test> bucketTests = client.getBucketTests(runscopeRunnerContext.getBucketId(), 1000);

            for(int i = 0; i < bucketTests.size(); i++) {
                runscopeRunnerContext.getTestsIds().add(bucketTests.get(i).getId());
            }
        }

        for (String testId : runscopeRunnerContext.getTestsIds()) {
            try {
                Test test = client.getTest(runscopeRunnerContext.getBucketId(), testId);
                tests.add(test);
            } catch (NotFoundException ex) {
                throw new RunBuildException("Cannot find test " + testId);
            }
        }

        logTests(tests);
        return tests;
    }

    private void logBucket(Bucket bucket) {
        logger.message(String.format("Fetched bucket %s : %s", runscopeRunnerContext.getBucketId(), bucket.getName()));
    }

    private void logTests(List<Test> tests) {
        logger.activityStarted("Tests", String.format("%d test(s) found", tests.size()), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);

        for(Test test : tests) {
            logger.activityStarted(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
            if(!StringUtil.isEmptyOrSpaces(test.getDescription())) logger.message(test.getDescription());
            logger.message(String.format("Id : %s", test.getId()));
            logger.message(String.format("Nested tests : %d", test.getSteps().size()));
            logger.activityFinished(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        }

        logger.activityStarted("Tests toto", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
    }
}
