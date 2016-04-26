package com.mangopay.teamcity.runscope.server;

import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RunscopeRunType extends RunType{
    private final PluginDescriptor pluginDescriptor;

    public RunscopeRunType(final RunTypeRegistry runTypeRegistry, final PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
        runTypeRegistry.registerRunType(this);
    }

    @NotNull
    @Override
    public String getType() {
        return RunscopeConstants.RUNNER_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return RunscopeConstants.RUNNER_DISPLAY_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return RunscopeConstants.RUNNER_DESCRIPTION;
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new RunscopeRunTypePropertiesProcessor();
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("editRunscopeRunParams.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return pluginDescriptor.getPluginResourcesPath("viewRunscopeRunParams.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        return new HashMap<String, String>();
    }
}