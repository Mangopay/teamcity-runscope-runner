package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String definesInitialVariables = parameters.get(RunscopeConstants.SETTINGS_DEFINES_INITIAL_VARIABLES);

        if(StringUtil.isEmptyOrSpaces(environment)) environment = "";
        if(StringUtil.isEmptyOrSpaces(testsIds)) testsIds = "";
        final List<String> tests = Arrays.asList(testsIds.split(AgentConstants.TESTS_SPLIT));

        this.buildRunnerContext = buildRunnerContext;
        this.runscopeRunnerContext = new RunscopeRunnerContext(token, bucket, environment, tests, logger);

        if(Boolean.parseBoolean(definesInitialVariables)) setInitialVariables();
        else this.runscopeRunnerContext.setInitialVariables(new HashMap<String, String>());
    }

    private void setInitialVariables() {
        final Map<String,String> initialVariables = new HashMap<String, String>();
        final int prefixLength = RunscopeConstants.RUNSCOPE_VAR_PREFIX.length();

        Map<String, String> buildParameters = buildRunnerContext.getConfigParameters();

        for(Map.Entry<String, String> parameter : buildParameters.entrySet()) {
            if(!parameter.getKey().startsWith(RunscopeConstants.RUNSCOPE_VAR_PREFIX)) continue;

            initialVariables.put(parameter.getKey().substring(prefixLength), parameter.getValue());
        }

        this.runscopeRunnerContext.setInitialVariables(initialVariables);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        logParameters();

        final RunscopeTestSetRunner runner = new RunscopeTestSetRunner(buildRunnerContext, runscopeRunnerContext);
        runner.call();

        return BuildFinishedStatus.FINISHED_SUCCESS;
    }

    private void logParameters() {
        logger.message("Bucket : " + runscopeRunnerContext.getBucketId().trim());
        if(!StringUtil.isEmptyOrSpaces(runscopeRunnerContext.getEnvironmentId())) logger.message("Environment : " + runscopeRunnerContext.getEnvironmentId().trim());
        if(runscopeRunnerContext.getTestsIds().size() > 0) logger.message("Tests : " + runscopeRunnerContext.getTestsIds().toString());
        logInitialVariables();
    }

    private void logInitialVariables() {
        Map<String, String> initialVariables = runscopeRunnerContext.getInitialVariables();
        if(initialVariables.size() == 0) return;

        logger.activityStarted("Initial variables", String.format("%d defined", initialVariables.size()), DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
        for(Map.Entry<String, String> variable : initialVariables.entrySet()) {
            logger.message(String.format("%s : %s", variable.getKey(), variable.getValue()));
        }
        logger.activityFinished("Initial variables", DefaultMessagesInfo.BLOCK_TYPE_INDENTATION);
    }
}
