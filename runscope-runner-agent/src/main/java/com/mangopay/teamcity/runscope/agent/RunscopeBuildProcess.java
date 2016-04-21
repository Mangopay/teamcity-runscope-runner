package com.mangopay.teamcity.runscope.agent;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private final BuildRunnerContext buildRunnerContext;
    private final RunscopeRunnerContext runscopeRunnerContext;
    private final ExecutorService threadPool;
    private final RunscopeTestSetRunner runner;

    RunscopeBuildProcess(@NotNull final BuildRunnerContext buildRunnerContext) throws RunBuildException {
        super(buildRunnerContext);

        this.buildRunnerContext = buildRunnerContext;
        final Map<String, String> parameters = this.buildRunnerContext.getRunnerParameters();
        final String token = parameters.get(RunscopeConstants.SETTINGS_TOKEN).trim();
        final String bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET).trim();
        String testsIds = parameters.get(RunscopeConstants.SETTINGS_TESTS);
        String excludedTestsIds = parameters.get(RunscopeConstants.SETTINGS_EXCLUDED_TESTS);
        String environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
        String initialVariables = parameters.get(RunscopeConstants.SETTINGS_VARIABLES);
        final boolean concurrentRunner = Boolean.parseBoolean(RunscopeConstants.SETTINGS_PARALLEL);

        final int threadPoolSize;
        if(concurrentRunner) {
            try {
                threadPoolSize = Integer.parseInt(parameters.get(RunscopeConstants.SETTINGS_PARALLEL_COUNT));
            } catch (NumberFormatException ex) {
                throw new RunBuildException("Number of tests to run simultaneously is not a number", ex);
            }
        }
        else threadPoolSize = 1;

        if(StringUtil.isEmptyOrSpaces(environment)) environment = "";
        if(StringUtil.isEmptyOrSpaces(testsIds)) testsIds = ",";
        if(StringUtil.isEmptyOrSpaces(testsIds)) excludedTestsIds = ",";
        if(StringUtil.isEmptyOrSpaces(initialVariables)) initialVariables = "";

        final List<String> tests = Arrays.asList(RunscopeConstants.MULTI_PARAMETER_SPLIT.split(testsIds));
        final List<String> excludedTests = Arrays.asList(RunscopeConstants.MULTI_PARAMETER_SPLIT.split(excludedTestsIds));

        runscopeRunnerContext = new RunscopeRunnerContext(token, bucket, environment, tests, excludedTests, logger);
        threadPool = Executors.newFixedThreadPool(threadPoolSize);

        setInitialVariables(initialVariables);

        runner = new RunscopeTestSetRunner(threadPool, buildRunnerContext, runscopeRunnerContext);
    }

    private void setInitialVariables(final String variablesParameter) {
        final Map<String,String> initialVariables = new HashMap<String, String>();

        final Matcher matcher = RunscopeConstants.SETTINGS_VARIABLES_PARSER.matcher(variablesParameter);
        final Map<String, String> buildParameters = buildRunnerContext.getConfigParameters();

        while(matcher.find()) {
            final String key = matcher.group("key");
            final String buildParameterKey = RunscopeConstants.RUNSCOPE_VAR_PREFIX + key;
            String value = matcher.group("value");

            if(StringUtil.isEmpty(value) && buildParameters.containsKey(buildParameterKey)) value = buildParameters.get(buildParameterKey);

            initialVariables.put(key, value);
        }

        runscopeRunnerContext.setInitialVariables(initialVariables);
    }
    @Override
    public BuildFinishedStatus call() throws RunBuildException {
        try {
            logParameters();
            return runner.call();
        }finally {
            threadPool.shutdown();
        }
    }

    @Override
    public void interrupt() {
        threadPool.shutdownNow();
        super.interrupt();
    }

    private void logParameters() {
        logger.message("Bucket : " + runscopeRunnerContext.getBucketId().trim());
        if(!StringUtil.isEmptyOrSpaces(runscopeRunnerContext.getEnvironmentId())) logger.message("Environment : " + runscopeRunnerContext.getEnvironmentId().trim());
        if(!runscopeRunnerContext.getTestsIds().isEmpty()) logger.message("Tests : " + runscopeRunnerContext.getTestsIds());
        if(!runscopeRunnerContext.getExcludedTestsIds().isEmpty()) logger.message("Excluded tests : " + runscopeRunnerContext.getExcludedTestsIds());
        logInitialVariables();
    }

    private void logInitialVariables() {
        final Map<String, String> initialVariables = runscopeRunnerContext.getInitialVariables();
        if(initialVariables.isEmpty()) return;

        logger.activityStarted("Initial variables", String.format("%d defined", initialVariables.size()), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        for(final Entry<String, String> variable : initialVariables.entrySet()) {
            logger.message(String.format("%s : %s", variable.getKey(), variable.getValue()));
        }
        logger.activityFinished("Initial variables", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
    }
}
