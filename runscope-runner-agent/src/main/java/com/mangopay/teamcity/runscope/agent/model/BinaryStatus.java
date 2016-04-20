package com.mangopay.teamcity.runscope.agent.model;

public enum BinaryStatus {
    FAILED("fail"),
    PASSED("pass");

    private final String status;

    BinaryStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
