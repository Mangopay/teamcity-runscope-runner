package com.mangopay.teamcity.runscope.agent;

import com.mangopay.teamcity.runscope.agent.model.Run;
import com.mangopay.teamcity.runscope.agent.model.Test;
import com.mangopay.teamcity.runscope.agent.model.TestResult;
import com.mangopay.teamcity.runscope.agent.model.Trigger;
import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import com.mangopay.teamcity.runscope.agent.client.RunscopeClient;
import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.util.StringUtil;

import javax.ws.rs.NotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class RunscopeTestRunner implements Callable<TestResult> {

    private final RunscopeClient client;
    private final Test test;
    private final String environment;
    private final BuildRunnerContext context;
    private final BuildProgressLogger logger;
    private final Map<String, String> initialVariables;

    public RunscopeTestRunner(final BuildRunnerContext context, final RunscopeClient client, final Test test, final String environment, final Map<String, String> initialVariables, final BuildProgressLogger logger) {
        this.context = context;
        this.client = client;
        this.test = test;
        this.environment = environment;
        this.logger = logger;
        this.initialVariables = initialVariables;
    }

    @Override
    public TestResult call() throws RunBuildException, InterruptedException {
        final String testName = test.getName();
        logger.logSuiteStarted(testName);

        final Trigger trigger = trigger();
        if(trigger.getRunsTotal() != 1) throw new RunBuildException(String.format("Expected 1 run but found %d. Please enable only one location on the specified environment", trigger.getRunsTotal()));

        final Run run = trigger.getRuns().get(0);
        final RunscopeRunWatcher watcher = new RunscopeRunWatcher(client, run, logger);
        final WatchResult result = watcher.call();
        setBuildParameters(result);

        logger.logSuiteFinished(testName);
        return result.getTestResult();
    }

    public Trigger trigger() throws RunBuildException {
        try {
            return client.trigger(test, environment, initialVariables);
        }
        catch(final NotFoundException ex) {
            String message = "Cannot trigger test %s";
            if(!StringUtil.isEmptyOrSpaces(environment)) message += " on environment %s";
            throw new RunBuildException(String.format(message, test.getId(), environment), ex);
        }
    }

    public void setBuildParameters(final WatchResult result) {
        final Map<String, String> variables = result.getVariables();
        final AgentRunningBuild build = context.getBuild();

        for(final Entry<String, String> runscopeVariable : variables.entrySet()) {
            build.addSharedConfigParameter(getBuildParameterName(runscopeVariable), runscopeVariable.getValue());
        }
    }

    private static String getBuildParameterName(final Entry<String, String> variable) {
        return RunscopeConstants.RUNSCOPE_VAR_PREFIX + variable.getKey();
    }
}
