package com.mangopay.teamcity.runscope.agent.model;

public enum RequestStatus {
    FAILED("fail"),
    PASSED("pass"),
    SKIPPED("skipped"),
    CANCELED("canceled");

    private final String status;

    RequestStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
