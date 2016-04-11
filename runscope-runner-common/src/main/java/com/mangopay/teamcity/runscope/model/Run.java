package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

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

    @XmlElement(name="bucket_key")
	@JsonProperty("bucket_key")
    public void setBucketKey(String bucketKey) {
        this.bucketKey = bucketKey;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    @XmlElement(name="environment_id")
	@JsonProperty("environment_id")
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    @XmlElement(name="environment_name")
	@JsonProperty("environment_name")
    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTestId() {
        return testId;
    }

    @XmlElement(name="test_id")
	@JsonProperty("test_id")
    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    @XmlElement(name="test_name")
	@JsonProperty("test_name")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestRunId() {
        return testRunId;
    }

    @XmlElement(name="test_run_id")
	@JsonProperty("test_run_id")
    public void setTestRunId(String testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestRunUrl() {
        return testRunUrl;
    }

    @XmlElement(name="test_run_url")
	@JsonProperty("test_run_url")
    public void setTestRunUrl(String testRunUrl) {
        this.testRunUrl = testRunUrl;
    }

    public String getTestUrl() {
        return testUrl;
    }

    @XmlElement(name="test_url")
	@JsonProperty("test_url")
    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
