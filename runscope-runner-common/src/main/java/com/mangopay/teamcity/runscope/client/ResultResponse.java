package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.model.TestResult;

class ResultResponse {
    private TestResult data;

    public TestResult getData() {
        return data;
    }

    public void setData(TestResult data) {
        this.data = data;
    }
}
