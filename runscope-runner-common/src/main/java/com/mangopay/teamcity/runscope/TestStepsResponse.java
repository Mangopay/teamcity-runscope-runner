package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.Step;

import java.util.List;

public class TestStepsResponse extends Response {
    private List<Step> data;

    public List<Step> getData() {
        return data;
    }

    public void setData(List<Step> data) {
        this.data = data;
    }
}
