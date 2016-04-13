package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.BuildMessage1;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private final RunscopeTestSetRunner runner;

    RunscopeBuildProcess(@NotNull final BuildRunnerContext context) {
        super(context);

        final Map<String, String> parameters = context.getRunnerParameters();
        final String token = parameters.get(RunscopeConstants.SETTINGS_APIKEY);
        final String bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET);
        final String tests = parameters.get(RunscopeConstants.SETTINGS_TESTS);
        final String environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);

        runner = new RunscopeTestSetRunner(token, bucket, tests, environment, logger);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        runner.call();
        return BuildFinishedStatus.FINISHED_SUCCESS;
    }


}
