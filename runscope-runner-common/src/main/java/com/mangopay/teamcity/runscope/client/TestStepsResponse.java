package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.model.Step;

import java.util.List;

class TestStepsResponse {
    private List<Step> data;

    public List<Step> getData() {
        return data;
    }

    public void setData(List<Step> data) {
        this.data = data;
    }
}
