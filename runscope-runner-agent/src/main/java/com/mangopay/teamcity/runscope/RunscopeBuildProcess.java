package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildRunnerContext;
import org.jetbrains.annotations.NotNull;

class RunscopeBuildProcess extends FutureBasedBuildProcess {
    RunscopeBuildProcess(@NotNull BuildRunnerContext context) {
        super(context);
    }

    @Override
    public BuildFinishedStatus call() throws Exception {
        try {

            return BuildFinishedStatus.FINISHED_SUCCESS;
        }
        catch(Exception e) {
            logger.message("Failed to run tests");
            logger.exception(e);
        }

        return BuildFinishedStatus.FINISHED_FAILED;
    }
}
