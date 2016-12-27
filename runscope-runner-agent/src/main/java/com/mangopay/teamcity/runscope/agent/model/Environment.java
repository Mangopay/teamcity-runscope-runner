package com.mangopay.teamcity.runscope.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Environment {
    private String id;
    private String name;
    private boolean stopOnFailure;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean getStopOnFailure() { return stopOnFailure; }

    @JsonProperty("stop_on_failure")
    public void setStopOnFailure(boolean stopOnFailure) { this.stopOnFailure = stopOnFailure; }
}
