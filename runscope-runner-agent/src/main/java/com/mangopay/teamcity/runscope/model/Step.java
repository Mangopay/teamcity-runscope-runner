package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Step {
    private String id;
    private String note;
    private StepType stepType;
    private String testName;
    private int duration;
    private List<StepAssertion> assertions;
    private List<Step> steps;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public StepType getStepType() {
        return stepType;
    }

	@JsonProperty("step_type")
    public void setStepType(final StepType stepType) {
        this.stepType = stepType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public String getTestName() {
        return testName;
    }

	@JsonProperty("test_name")
    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public List<StepAssertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(final List<StepAssertion> assertions) {
        this.assertions = assertions;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }
}
