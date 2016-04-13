package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Run;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import com.mangopay.teamcity.runscope.model.Trigger;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.concurrent.Callable;

public class RunscopeTestRunner implements Callable<TestResult> {

    private final RunscopeClient client;
    private final Test test;
    private final String environment;
    private final BuildProgressLogger logger;

    public RunscopeTestRunner(final RunscopeClient client, final Test test, final String environment, final BuildProgressLogger logger) {
        this.client = client;
        this.test = test;
        this.environment = environment;
        this.logger = logger.getFlowLogger(this.test.getId());
    }

    @Override
    public TestResult call() throws RunBuildException, InterruptedException {
        TestResult result = null;

        String testName = test.getName();
        logger.logSuiteStarted(testName);

        Trigger trigger = client.trigger(test, environment);
        for(Run run : trigger.getRuns()) {
            RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, logger);
            result = watcher.watch();
        }

        logger.logSuiteFinished(testName);
        return result;
    }
}
