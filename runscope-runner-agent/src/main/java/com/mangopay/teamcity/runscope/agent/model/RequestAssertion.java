package com.mangopay.teamcity.runscope.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAssertion {
    private String source;
    private String comparison;
    private String actualValue;
    private String targetValue;
    private String error;
    private String property;
    private BinaryStatus result;

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(final String comparison) {
        this.comparison = comparison;
    }

    public String getActualValue() {
        return actualValue;
    }

	@JsonProperty("actual_value")
    public void setActualValue(final String actualValue) {
        this.actualValue = actualValue;
    }

    public String getTargetValue() {
        return targetValue;
    }

	@JsonProperty("target_value")
    public void setTargetValue(final String targetValue) {
        this.targetValue = targetValue;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(final String property) {
        this.property = property;
    }

    public BinaryStatus getResult() {
        return result;
    }

    public void setResult(final BinaryStatus result) {
        this.result = result;
    }
}
