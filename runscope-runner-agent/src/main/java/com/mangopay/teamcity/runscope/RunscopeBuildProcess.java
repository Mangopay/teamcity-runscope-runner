package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.messages.BuildMessage1;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private String token;
    private String bucket;
    private String tests;
    private String environment;

    RunscopeBuildProcess(@NotNull final BuildRunnerContext context) {
        super(context);

        Map<String, String> parameters = context.getRunnerParameters();
        token = parameters.get(RunscopeConstants.SETTINGS_APIKEY);
        bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET);
        tests = parameters.get(RunscopeConstants.SETTINGS_TESTS);
        environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        RunscopeTestSet runscopeTestSet = new RunscopeTestSet(token, bucket, tests, environment, logger);
        logRunStart();
        runscopeTestSet.run();

        return BuildFinishedStatus.FINISHED_SUCCESS;
    }

    private void logRunStart() {
        logger.message("Running Runscope tests");
        logger.message("Bucket : " + bucket);
        logger.message("Environment : " + environment);
        logger.message("Tests : " + tests);

    }
}
