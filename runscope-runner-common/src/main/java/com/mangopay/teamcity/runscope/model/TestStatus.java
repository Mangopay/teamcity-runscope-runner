package com.mangopay.teamcity.runscope.model;

public enum TestStatus {
    CANCELED("canceled"),
    FAILED("fail"),
    PASSED("pass"),
    WORKING("working"),
    UNKNOWN("unknown");

    private final String status;

    TestStatus(String status) {
        this.status = status;
    }

    public boolean isDone() {
        return this == FAILED || this == PASSED || this == CANCELED;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
