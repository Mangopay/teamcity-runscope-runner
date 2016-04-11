package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

public class TestResult {
    private Integer assertionsDefined;
    private Integer assertionsFailed;
    private Integer assertionsPassed;
    private TestStatus result;
    private Date finishedAt;
    private List<Request> requests;

    public int getAssertionsDefined() {
        return assertionsDefined;
    }

    @XmlElement(name="assertions_defined")
	@JsonProperty("assertions_defined")
    public void setAssertionsDefined(Integer assertionsDefined) {
        this.assertionsDefined = assertionsDefined;
    }

    public Integer getAssertionsFailed() {
        return assertionsFailed;
    }

    @XmlElement(name="assertions_failed")
	@JsonProperty("assertions_failed")
    public void setAssertionsFailed(Integer assertionsFailed) {
        this.assertionsFailed = assertionsFailed;
    }

    public Integer getAssertionsPassed() {
        return assertionsPassed;
    }

    @XmlElement(name="assertions_passed")
	@JsonProperty("assertions_passed")
    public void setAssertionsPassed(Integer assertionsPassed) {
        this.assertionsPassed = assertionsPassed;
    }

    public TestStatus getResult() {
        return result;
    }

    public void setResult(TestStatus result) {
        this.result = result;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    @XmlElement(name="finished_at")
	@JsonProperty("finished_at")
    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
