package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.model.Bucket;

class BucketResponse extends Response {
    private Bucket data;

    public Bucket getData() {
        return data;
    }

    public void setData(Bucket data) {
        this.data = data;
    }
}
