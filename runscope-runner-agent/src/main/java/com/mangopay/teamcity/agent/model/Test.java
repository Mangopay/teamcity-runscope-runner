package com.mangopay.teamcity.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Test {
    private String id;
    private String name;
    private String description;
    private String triggerUrl;
    private List<Step> steps;
    private List<Environment> environments;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(final List<Environment> environments) {
        this.environments = environments;
    }


    public String getTriggerUrl() {
        return triggerUrl;
    }

	@JsonProperty("trigger_url")
    public void setTriggerUrl(final String triggerUrl) {
        this.triggerUrl = triggerUrl;
    }

}
