package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Step {
    private String id;
    private String note;
    private StepType stepType;
    private String testName;
    private int duration;
    private List<StepAssertion> assertions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public StepType getStepType() {
        return stepType;
    }

    @XmlElement(name="step_type")
	@JsonProperty("step_type")
    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTestName() {
        return testName;
    }

    @XmlElement(name="test_name")
	@JsonProperty("test_name")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<StepAssertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<StepAssertion> assertions) {
        this.assertions = assertions;
    }
}
