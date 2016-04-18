package com.mangopay.teamcity.runscope;

import org.jetbrains.annotations.NotNull;

public class RunscopeConstantsBean {
    @NotNull
    public String getApiKey() { return RunscopeConstants.SETTINGS_APIKEY; }

    @NotNull
    public String getBucketKey() { return RunscopeConstants.SETTINGS_BUCKET; }

    @NotNull
    public String getTestsKey() { return RunscopeConstants.SETTINGS_TESTS; }

    @NotNull
    public String getEnvironmentKey() { return RunscopeConstants.SETTINGS_ENVIRONMENT; }

    @NotNull
    public String getVariablesKey() { return RunscopeConstants.SETTINGS_VARIABLES; }

    @NotNull
    public String getRunscopeVariablesPrefix() { return RunscopeConstants.RUNSCOPE_VAR_PREFIX; }

}
