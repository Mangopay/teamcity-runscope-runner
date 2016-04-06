package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.agent.BuildProgressLogger;

public class RunscopeRunWatcher {
    private RunscopeClient client;
    private Run run;
    private BuildProgressLogger logger;

    public RunscopeRunWatcher(RunscopeClient client, Run run, BuildProgressLogger logger) {
        this.client = client;
        this.run = run;
        this.logger = logger;
    }

    public TestResult watch() {
        Boolean done = false;
        TestResult result = null;

        while(!done) {
            result = client.getRunResult(this.run);
            //todo : intermediate test logging about requests
            done = isDone(result);
        }

        return result;
    }

    private Boolean isDone(TestResult result) {
        String status = result.getResult();
        return ("pass".equals(status) || "fail".equals(status) || "canceled".equals(status));
    }
}
