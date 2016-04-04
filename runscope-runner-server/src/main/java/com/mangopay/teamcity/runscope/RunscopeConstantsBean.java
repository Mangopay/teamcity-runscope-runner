package com.mangopay.teamcity.runscope;

import org.jetbrains.annotations.NotNull;

public class RunscopeConstantsBean {
    @NotNull
    public String getApiKey() { return RunscopeConstants.SETTINGS_APIKEY; }

    @NotNull
    public String getBucketKey() { return RunscopeConstants.SETTINGS_BUCKET; }

    @NotNull
    public String getTestKey() { return RunscopeConstants.SETTINGS_TEST; }

    @NotNull
    public String getEnvironmentKey() { return RunscopeConstants.SETTINGS_ENVIRONMENT; }
}
