package com.mangopay.teamcity.runscope.agent.model;

public enum StepType {
    REQUEST("request"),
    PAUSE("pause"),
    CONDITION("condition"),
    GHOST_INSPECTOR("ghost-inspector"),
    INCOMING("inbound"),
    INITIAL_SCRIPT("initial-script");

    private final String type;

    StepType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
