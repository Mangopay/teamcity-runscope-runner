package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.BuildMessage1;
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
        token = parameters.get(RunscopeConstants.SETTINGS_APIKEY);
        bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET);
        tests = parameters.get(RunscopeConstants.SETTINGS_TESTS);
        environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        final RunscopeTestSet runscopeTestSet = new RunscopeTestSet(token, bucket, tests, environment, logger);
        runscopeTestSet.run();

        return BuildFinishedStatus.FINISHED_SUCCESS;
    }


}
