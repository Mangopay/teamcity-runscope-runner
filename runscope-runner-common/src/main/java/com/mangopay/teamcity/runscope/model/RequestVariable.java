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

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public BinaryStatus getResult() {
        return result;
    }

    public void setResult(BinaryStatus result) {
        this.result = result;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
