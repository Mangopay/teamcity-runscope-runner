package com.mangopay.teamcity.runscope.agent;

import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.*;
import org.jetbrains.annotations.NotNull;

public class RunscopeBuildRunner implements AgentBuildRunner, AgentBuildRunnerInfo {
    @NotNull
    @Override
    public BuildProcess createBuildProcess(@NotNull final AgentRunningBuild runningBuild, @NotNull final BuildRunnerContext context) throws RunBuildException {
        return new RunscopeBuildProcess(context);
    }

    @NotNull
    @Override
    public AgentBuildRunnerInfo getRunnerInfo() {
        return this;
    }

    @NotNull
    @Override
    public String getType() {
        return RunscopeConstants.RUNNER_TYPE;
    }

    @Override
    public boolean canRun(@NotNull final BuildAgentConfiguration agentConfiguration) {
        return true;
    }
}
