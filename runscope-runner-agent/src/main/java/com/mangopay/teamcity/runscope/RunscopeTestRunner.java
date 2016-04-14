package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.client.RunscopeClient;
import com.mangopay.teamcity.runscope.model.Run;
import com.mangopay.teamcity.runscope.model.Test;
import com.mangopay.teamcity.runscope.model.TestResult;
import com.mangopay.teamcity.runscope.model.Trigger;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.util.StringUtil;

import javax.ws.rs.NotFoundException;
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
        this.logger = logger;
    }

    @Override
    public TestResult call() throws RunBuildException, InterruptedException {
        BuildProgressLogger logger = this.logger.getThreadLogger();
        TestResult result = null;

        final String testName = test.getName();
        logger.logSuiteStarted(testName);

        final Trigger trigger = trigger(test, environment);
        for(Run run : trigger.getRuns()) {
            RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, logger);
            result = watcher.call();
        }

        logger.logSuiteFinished(testName);
        return result;
    }

    public Trigger trigger(Test test, String environment) throws RunBuildException {
        try {
            return client.trigger(test, environment);
        }
        catch(NotFoundException ex) {
            String message = "Cannot trigger test %s";
            if(!StringUtil.isEmptyOrSpaces(environment)) message += " on environment %s";
            throw new RunBuildException(String.format(message, test.getId(), environment));
        }
    }
}
