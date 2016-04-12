package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.*;
import org.jetbrains.annotations.NotNull;

public class RunscopeBuildRunner implements AgentBuildRunner, AgentBuildRunnerInfo {
    @NotNull
    @Override
    public BuildProcess createBuildProcess(@NotNull final AgentRunningBuild agentRunningBuild, @NotNull final BuildRunnerContext buildRunnerContext) throws RunBuildException {
        return new RunscopeBuildProcess(buildRunnerContext);
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
    public boolean canRun(@NotNull BuildAgentConfiguration buildAgentConfiguration) {
        return true;
    }
}
