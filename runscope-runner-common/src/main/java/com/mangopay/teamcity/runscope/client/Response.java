package com.mangopay.teamcity.runscope.client;

import com.mangopay.teamcity.runscope.RunscopeError;
import com.mangopay.teamcity.runscope.RunscopeMeta;

class Response {
    private RunscopeMeta meta;
    private RunscopeError error;

    public RunscopeMeta getMeta() {
        return meta;
    }

    public void setMeta(RunscopeMeta meta) {
        this.meta = meta;
    }

    public RunscopeError getError() {
        return error;
    }

    public void setError(RunscopeError error) {
        this.error = error;
    }
}
