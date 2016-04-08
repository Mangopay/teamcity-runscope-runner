package com.mangopay.teamcity.runscope.model;

public enum RequestStatus {
    FAILED("fail"),
    PASSED("pass"),
    CANCELED("canceled"),
    UNKNOWN("unknown");

    private final String status;

    RequestStatus(String status) {
        this.status = status;
    }

    public boolean isDone() {
        return this == FAILED || this == PASSED ;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
