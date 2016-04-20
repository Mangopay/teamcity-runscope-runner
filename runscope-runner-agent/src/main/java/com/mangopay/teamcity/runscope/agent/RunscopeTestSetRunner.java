package com.mangopay.teamcity.runscope.agent;

import com.mangopay.teamcity.runscope.agent.client.RunscopeClient;
import com.mangopay.teamcity.runscope.agent.model.Bucket;
import com.mangopay.teamcity.runscope.agent.model.Test;
import com.mangopay.teamcity.runscope.agent.model.TestResult;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

public class RunscopeTestSetRunner implements Callable<BuildFinishedStatus> {

    private final ExecutorService threadPool;
    private final RunscopeClient client;
    private final BuildProgressLogger logger;
    private final RunscopeRunnerContext runscopeRunnerContext;
    private final BuildRunnerContext buildRunnerContext;
    private final List<Future<TestResult>> futures;

    public RunscopeTestSetRunner(@NotNull final ExecutorService threadPool, @NotNull final BuildRunnerContext buildRunnerContext, @NotNull final RunscopeRunnerContext runscopeRunnerContext) {
        this.threadPool = threadPool;
        this.buildRunnerContext = buildRunnerContext;
        this.runscopeRunnerContext = runscopeRunnerContext;
        client = new RunscopeClient(this.runscopeRunnerContext.getToken());
        logger = this.runscopeRunnerContext.getLogger();

        futures = new Vector<Future<TestResult>>();
    }

    @Override
    public BuildFinishedStatus call() throws RunBuildException {
        logger.activityStarted("Fetching data", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        final Bucket bucket = getBucket();
        final List<Test> tests = getTests();
        logger.activityFinished("Fetching data", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);

        logger.logSuiteStarted(bucket.getName());

        final CompletionService<TestResult> completionService = new ExecutorCompletionService<TestResult>(threadPool);

        for(final Test test : tests) {
            final Callable<TestResult> runner = new RunscopeTestRunner(buildRunnerContext, client, test, runscopeRunnerContext.getEnvironmentId(), runscopeRunnerContext.getInitialVariables(), logger.getFlowLogger(test.getId()));
            futures.add(completionService.submit(runner));
        }

        for(int i = 0; i < tests.size(); i++) {
            try {
                final Future<TestResult> result = completionService.take();
                result.get();
            } catch (final InterruptedException ex) {
            } catch (final ExecutionException ex) {
                final Throwable cause = ex.getCause();
                logger.exception(ex);
                logger.buildFailureDescription(cause.getMessage());
            }
        }

        logger.logSuiteFinished(bucket.getName());
        return BuildFinishedStatus.FINISHED_SUCCESS;
    }

    public void interrupt() {
        threadPool.shutdownNow();
    }

    private Bucket getBucket() throws RunBuildException {
        try {
            final Bucket bucket = client.getBucket(runscopeRunnerContext.getBucketId());
            logBucket(bucket);

            return bucket;
        }
        catch(final NotFoundException ex) {
            throw new RunBuildException("Cannot find bucket " + runscopeRunnerContext.getBucketId(), ex);
        }
    }

    private List<Test> getTests() throws RunBuildException {
        final List<Test> tests = new ArrayList<Test>();
        final List<String> testsIds = runscopeRunnerContext.getTestsIds();

        if(testsIds.isEmpty()) {
            logger.message("No test specified, finding bucket tests.");
            final List<Test> bucketTests = client.getBucketTests(runscopeRunnerContext.getBucketId(), 1000);

            for (final Test bucketTest : bucketTests) {
                testsIds.add(bucketTest.getId());
            }
        }

        for (final String testId : testsIds) {
            try {
                final Test test = client.getTest(runscopeRunnerContext.getBucketId(), testId);
                tests.add(test);
            } catch (final NotFoundException ex) {
                throw new RunBuildException("Cannot find test " + testId, ex);
            }
        }

        logTests(tests);
        return tests;
    }

    private void logBucket(final Bucket bucket) {
        logger.message(String.format("Fetched bucket %s : %s", runscopeRunnerContext.getBucketId(), bucket.getName()));
    }

    private void logTests(final List<Test> tests) {
        logger.activityStarted("Tests", String.format("%d test(s) found", tests.size()), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);

        for(final Test test : tests) {
            logger.activityStarted(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
            if(!StringUtil.isEmptyOrSpaces(test.getDescription())) logger.message(test.getDescription());
            logger.message(String.format("Id : %s", test.getId()));
            logger.message(String.format("Nested tests : %d", test.getSteps().size()));
            logger.activityFinished(test.getName(), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        }

        logger.activityStarted("Tests toto", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
    }
}
