package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.FlowLogger;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CancellationException;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    private String token;
    private String bucket;
    private String test;
    private String environment;

    RunscopeBuildProcess(@NotNull BuildRunnerContext context) {
        super(context);

        Map<String, String> parameters = context.getRunnerParameters();
        token = parameters.get(RunscopeConstants.SETTINGS_APIKEY);
        bucket = parameters.get(RunscopeConstants.SETTINGS_BUCKET);
        test = parameters.get(RunscopeConstants.SETTINGS_TEST);
        environment = parameters.get(RunscopeConstants.SETTINGS_ENVIRONMENT);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        RunscopeTestSet runscopeTestSet = new RunscopeTestSet(token, bucket, test, environment, this.logger);
        runscopeTestSet.run();

        return BuildFinishedStatus.FINISHED_SUCCESS;
    }
}
