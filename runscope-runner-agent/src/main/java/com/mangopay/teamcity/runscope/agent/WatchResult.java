package com.mangopay.teamcity.runscope.agent;

import com.mangopay.teamcity.runscope.agent.model.TestResult;

import java.util.HashMap;
import java.util.Map;

public class WatchResult {
    private TestResult testResult;
    private Map<String, String> variables;

    public WatchResult() {
        variables = new HashMap<String, String>();
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void putVariable(final String name, final String value) {
        variables.put(name, value);
    }
}
