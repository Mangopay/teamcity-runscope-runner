package com.mangopay.teamcity.runscope.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAssertion extends AssertionBase {
    private String source;
    private String comparison;
    private String actualValue;
    private String targetValue;
    private String property;

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

    public String getProperty() {
        return property;
    }

    public void setProperty(final String property) {
        this.property = property;
    }
}
