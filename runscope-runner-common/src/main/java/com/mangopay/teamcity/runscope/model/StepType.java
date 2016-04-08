package com.mangopay.teamcity.runscope.model;

public enum StepType {
    REQUEST("request"),
    PAUSE("pause"),
    CONDITION("condition"),
    GHOST_INSPECTOR("ghost-inspector"),
    INITIAL_SCRIPT("initial-script"),
    UNKNOWN("unknown");

    private final String type;

    StepType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
