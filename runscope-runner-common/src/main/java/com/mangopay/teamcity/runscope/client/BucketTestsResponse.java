package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.model.Test;

import java.util.List;

class BucketTestsResponse extends Response {
    private List<Test> data;

    public List<Test> getData() {
        return data;
    }

    public void setData(List<Test> data) {
        this.data = data;
    }
}
