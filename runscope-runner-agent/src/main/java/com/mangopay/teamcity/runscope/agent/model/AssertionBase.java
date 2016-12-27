package com.mangopay.teamcity.runscope.agent.model;

public class AssertionBase {
    private String error;
    private BinaryStatus result;

    public BinaryStatus getResult() { return result; }

    public void setResult(BinaryStatus result) { this.result = result; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }
}
