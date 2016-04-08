package com.mangopay.teamcity.runscope.model;

import javax.xml.bind.annotation.XmlElement;

public class RequestAssertion {
    private String source;
    private String comparison;
    private String actualValue;
    private String targetValue;
    private String error;
    private String property;
    private AssertionStatus result;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getActualValue() {
        return actualValue;
    }

    @XmlElement(name="actual_value")
    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getTargetValue() {
        return targetValue;
    }

    @XmlElement(name="target_value")
    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public AssertionStatus getResult() {
        return result;
    }

    public void setResult(AssertionStatus result) {
        this.result = result;
    }
}
