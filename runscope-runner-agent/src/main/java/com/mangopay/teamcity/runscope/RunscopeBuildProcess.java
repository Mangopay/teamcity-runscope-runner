package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private final BuildRunnerContext buildRunnerContext;
    private final RunscopeRunnerContext runscopeRunnerContext;

    RunscopeBuildProcess(@NotNull final BuildRunnerContext buildRunnerContext) {
        super(buildRunnerContext);

        final Map<String, String> parameters = buildRunnerContext.getRunnerParameters();
        final String token = parameters.get(RunscopeConstants.SETTINGS_APIKEY).trim();
        final String bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET).trim();
        String environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
        String testsIds = parameters.get(RunscopeConstants.SETTINGS_TESTS);
        final String initialVariables = parameters.get(RunscopeConstants.SETTINGS_VARIABLES);

        if(StringUtil.isEmptyOrSpaces(environment)) environment = "";
        if(StringUtil.isEmptyOrSpaces(testsIds)) testsIds = "";
        final List<String> tests = Arrays.asList(testsIds.split(RunscopeConstants.MULTI_PARAMETER_SPLIT));

        this.buildRunnerContext = buildRunnerContext;
        runscopeRunnerContext = new RunscopeRunnerContext(token, bucket, environment, tests, logger);

        setInitialVariables(initialVariables);
    }

    private void setInitialVariables(final String variablesParameter) {
        final Map<String,String> initialVariables = new HashMap<String, String>();

        final Pattern variablesPattern = Pattern.compile(RunscopeConstants.SETTINGS_VARIABLES_PARSER, Pattern.MULTILINE);
        final Matcher matcher = variablesPattern.matcher(variablesParameter);
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
        logParameters();

        final RunscopeTestSetRunner runner = new RunscopeTestSetRunner(buildRunnerContext, runscopeRunnerContext);
        return runner.call();
    }

    private void logParameters() {
        logger.message("Bucket : " + runscopeRunnerContext.getBucketId().trim());
        if(!StringUtil.isEmptyOrSpaces(runscopeRunnerContext.getEnvironmentId())) logger.message("Environment : " + runscopeRunnerContext.getEnvironmentId().trim());
        if(!runscopeRunnerContext.getTestsIds().isEmpty()) logger.message("Tests : " + runscopeRunnerContext.getTestsIds());
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
