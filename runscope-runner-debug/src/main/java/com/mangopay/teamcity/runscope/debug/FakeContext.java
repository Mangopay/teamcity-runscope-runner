package com.mangopay.teamcity.runscope.debug;

import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildParametersMap;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.ToolCannotBeFoundException;
import jetbrains.buildServer.parameters.ValueResolver;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class FakeContext implements BuildRunnerContext {

    private final Map<String, String> runnerParameters;
    private final AgentRunningBuild agentRunningBuild;

    public FakeContext(final String token, final String bucket, final String tests, final String excludedTests, final String environment) {
        runnerParameters = new HashMap<String, String>() {
            {
                put(RunscopeConstants.SETTINGS_TOKEN, token);
                put(RunscopeConstants.SETTINGS_BUCKET, bucket);
                put(RunscopeConstants.SETTINGS_TESTS, tests);
                put(RunscopeConstants.SETTINGS_EXCLUDED_TESTS, excludedTests);
                put(RunscopeConstants.SETTINGS_ENVIRONMENT, environment);
            }
        };

        agentRunningBuild = new FakeBuild();
    }

    @Override
    public String getId() {
        return null;
    }

    @NotNull
    @Override
    public AgentRunningBuild getBuild() {
        return agentRunningBuild;
    }

    @NotNull
    @Override
    public File getWorkingDirectory() {
        return null;
    }

    @Override
    public String getRunType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @NotNull
    @Override
    public BuildParametersMap getBuildParameters() {
        return null;
    }

    @Override
    public Map<String, String> getConfigParameters() {
        return null;
    }

    @NotNull
    @Override
    public Map<String, String> getRunnerParameters() {
        return runnerParameters;
    }

    @Override
    public void addSystemProperty(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addEnvironmentVariable(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addConfigParameter(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addRunnerParameter(@NotNull String key, @NotNull String value) {

    }

    @NotNull
    @Override
    public ValueResolver getParametersResolver() {
        return null;
    }

    @NotNull
    @Override
    public String getToolPath(@NotNull String toolName) throws ToolCannotBeFoundException {
        return null;
    }

    @Override
    public boolean parametersHaveReferencesTo(@NotNull Collection<String> keys) {
        return false;
    }
}
