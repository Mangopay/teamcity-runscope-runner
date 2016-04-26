package com.mangopay.teamcity.runscope.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Run {
    private String bucketKey;
    private String environmentId;
    private String environmentName;
    private String region;
    private String testId;
    private String testName;
    private String testRunId;
    private String testRunUrl;
    private String testUrl;
    private String url;

    public String getBucketKey() {
        return bucketKey;
    }

	@JsonProperty("bucket_key")
    public void setBucketKey(final String bucketKey) {
        this.bucketKey = bucketKey;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

	@JsonProperty("environment_id")
    public void setEnvironmentId(final String environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

	@JsonProperty("environment_name")
    public void setEnvironmentName(final String environmentName) {
        this.environmentName = environmentName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getTestId() {
        return testId;
    }

	@JsonProperty("test_id")
    public void setTestId(final String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

	@JsonProperty("test_name")
    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public String getTestRunId() {
        return testRunId;
    }

	@JsonProperty("test_run_id")
    public void setTestRunId(final String testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestRunUrl() {
        return testRunUrl;
    }

	@JsonProperty("test_run_url")
    public void setTestRunUrl(final String testRunUrl) {
        this.testRunUrl = testRunUrl;
    }

    public String getTestUrl() {
        return testUrl;
    }

	@JsonProperty("test_url")
    public void setTestUrl(final String testUrl) {
        this.testUrl = testUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
