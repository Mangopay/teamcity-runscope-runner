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

	@JsonProperty("assertions_defined")
    public void setAssertionsDefined(final Integer assertionsDefined) {
        this.assertionsDefined = assertionsDefined;
    }

    public Integer getAssertionsFailed() {
        return assertionsFailed;
    }

	@JsonProperty("assertions_failed")
    public void setAssertionsFailed(final Integer assertionsFailed) {
        this.assertionsFailed = assertionsFailed;
    }

    public Integer getAssertionsPassed() {
        return assertionsPassed;
    }

	@JsonProperty("assertions_passed")
    public void setAssertionsPassed(final Integer assertionsPassed) {
        this.assertionsPassed = assertionsPassed;
    }

    public TestStatus getResult() {
        return result;
    }

    public void setResult(final TestStatus result) {
        this.result = result;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

	@JsonProperty("finished_at")
    public void setFinishedAt(final Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(final List<Request> requests) {
        this.requests = requests;
    }
}
