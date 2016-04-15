package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }


    public String getTriggerUrl() {
        return triggerUrl;
    }

	@JsonProperty("trigger_url")
    public void setTriggerUrl(String triggerUrl) {
        this.triggerUrl = triggerUrl;
    }

}
