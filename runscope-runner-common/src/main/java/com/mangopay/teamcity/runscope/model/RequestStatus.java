package com.mangopay.teamcity.runscope.model;

public enum RequestStatus {
    FAILED("fail"),
    PASSED("pass"),
    CANCELED("canceled");

    private final String status;

    RequestStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
