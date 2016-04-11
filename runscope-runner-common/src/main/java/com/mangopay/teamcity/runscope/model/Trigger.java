package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Trigger {
    private List<Run> runs;
    private int runsFailed;
    private int runsStarted;
    private int runsTotal;

    public List<Run> getRuns() {
        return runs;
    }

    public void setRuns(List<Run> runs) {
        this.runs = runs;
    }

    public int getRunsFailed() {
        return runsFailed;
    }

    @XmlElement(name="runs_failed")
	@JsonProperty("runs_failed")
    public void setRunsFailed(int runsFailed) {
        this.runsFailed = runsFailed;
    }

    public int getRunsStarted() {
        return runsStarted;
    }

    @XmlElement(name="runs_started")
	@JsonProperty("runs_started")
    public void setRunsStarted(int runsStarted) {
        this.runsStarted = runsStarted;
    }

    public int getRunsTotal() {
        return runsTotal;
    }

    @XmlElement(name="runs_total")
	@JsonProperty("runs_total")
    public void setRunsTotal(int runsTotal) {
        this.runsTotal = runsTotal;
    }
}
