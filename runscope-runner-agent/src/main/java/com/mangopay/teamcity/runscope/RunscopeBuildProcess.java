package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.DefaultMessagesInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private final String token;
    private final String bucket;
    private final String tests;
    private final String environment;

    RunscopeBuildProcess(@NotNull final BuildRunnerContext context) {
        super(context);

        final Map<String, String> parameters = context.getRunnerParameters();
        token = parameters.get(RunscopeConstants.SETTINGS_APIKEY).trim();
        bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET).trim();
        environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
        tests = parameters.get(RunscopeConstants.SETTINGS_TESTS);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        logParameters();

        final RunscopeTestSetRunner runner = new RunscopeTestSetRunner(token, bucket, tests, environment, logger);
        runner.call();

        return BuildFinishedStatus.FINISHED_SUCCESS;
    }

    private void logParameters() {
        logger.message("Bucket : " + bucket.trim());
        if(!StringUtil.isEmptyOrSpaces(environment)) logger.message("Environment : " + environment.trim());
        if(!StringUtil.isEmptyOrSpaces(tests)) logger.message("Tests : " + tests.trim().replaceAll(AgentConstants.TESTS_SPLIT, " "));
    }


}
