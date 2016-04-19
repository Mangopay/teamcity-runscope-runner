package com.mangopay.teamcity.runscope.model;

public class RequestVariable {
    private String error;
    private String name;
    private String property;
    private BinaryStatus result;
    private String source;
    private String value;

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
