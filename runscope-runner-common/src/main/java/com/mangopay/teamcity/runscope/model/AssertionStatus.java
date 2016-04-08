package com.mangopay.teamcity.runscope.model;

public enum AssertionStatus {
    FAILED("fail"),
    PASSED("pass"),
    UNKNOWN("unknown");

    private final String status;

    AssertionStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
