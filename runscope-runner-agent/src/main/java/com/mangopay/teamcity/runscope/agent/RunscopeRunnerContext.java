package com.mangopay.teamcity.runscope.agent;

import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.List;
import java.util.Map;

public class RunscopeRunnerContext {
    private String token;
    private String bucketId;
    private List<String> testsIds;
    private List<String> excludedTestsIds;
    private String environmentId;
    private BuildProgressLogger logger;

    private Map<String, String> initialVariables;

    public RunscopeRunnerContext(String token, String bucketId, String environmentId, List<String> testsIds, List<String> excludedTestsIds, BuildProgressLogger logger) {
        this.bucketId = bucketId;
        this.environmentId = environmentId;
        this.token = token;
        this.testsIds = testsIds;
        this.excludedTestsIds = excludedTestsIds;
        this.logger = logger;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public List<String> getTestsIds() {
        return testsIds;
    }

    public void setTestsIds(List<String> testsIds) {
        this.testsIds = testsIds;
    }

    public List<String> getExcludedTestsIds() {
        return excludedTestsIds;
    }

    public void setExcludedTestsIds(List<String> excludedTestsIds) {
        this.excludedTestsIds = excludedTestsIds;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public BuildProgressLogger getLogger() {
        return logger;
    }

    public void setLogger(BuildProgressLogger logger) {
        this.logger = logger;
    }

    public Map<String, String> getInitialVariables() {
        return initialVariables;
    }

    public void setInitialVariables(Map<String, String> initialVariables) {
        this.initialVariables = initialVariables;
    }
}
