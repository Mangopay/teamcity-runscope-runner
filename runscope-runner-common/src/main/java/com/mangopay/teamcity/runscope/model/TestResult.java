package com.mangopay.teamcity.runscope.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class TestResult {
    private int assertionsDefined;
    private int assertionsFailed;
    private int assertionsPassed;
    private String result;
    private Date finishedAt;

    public int getAssertionsDefined() {
        return assertionsDefined;
    }

    @XmlElement(name="assertions_defined")
    public void setAssertionsDefined(int assertionsDefined) {
        this.assertionsDefined = assertionsDefined;
    }

    public int getAssertionsFailed() {
        return assertionsFailed;
    }

    @XmlElement(name="assertions_failed")
    public void setAssertionsFailed(int assertionsFailed) {
        this.assertionsFailed = assertionsFailed;
    }

    public int getAssertionsPassed() {
        return assertionsPassed;
    }

    @XmlElement(name="assertions_passed")
    public void setAssertionsPassed(int assertionsPassed) {
        this.assertionsPassed = assertionsPassed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    @XmlElement(name="finished_at")
    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
}
