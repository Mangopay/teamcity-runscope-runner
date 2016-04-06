package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.TestResult;

public class ResultResponse extends Response {
    private TestResult data;

    public TestResult getData() {
        return data;
    }

    public void setData(TestResult data) {
        this.data = data;
    }
}
