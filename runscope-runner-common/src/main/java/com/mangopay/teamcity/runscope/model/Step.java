package com.mangopay.teamcity.runscope.model;

import javax.xml.bind.annotation.XmlElement;

public class Step {
    private String id;
    private String note;
    private StepType stepType;
    private String testName;
    private int duration;

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
    public void setTestName(String testName) {
        this.testName = testName;
    }
}
