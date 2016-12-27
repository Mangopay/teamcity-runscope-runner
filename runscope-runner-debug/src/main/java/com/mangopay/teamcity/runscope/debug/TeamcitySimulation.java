package com.mangopay.teamcity.runscope.debug;


import com.mangopay.teamcity.runscope.agent.RunscopeBuildRunner;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildRunnerContext;

public class TeamcitySimulation {

    //simulate workflow that teamcity should implement on a test run. usefull for quick debugging
    public static void main(String[] args) throws Exception {
        final String token = args[0];
        final String bucket = args[1];
        final String tests =  args.length > 2 ? args[2] : "";
        final String environment = args.length > 3 ? args[3] : "";
        final String excludedTests =  args.length > 4 ? args[4] : "";

        BuildRunnerContext context = new FakeContext(token, bucket, tests, excludedTests, environment);
        RunscopeBuildRunner buildRunner = new RunscopeBuildRunner();
        final BuildProcess buildProcess = buildRunner.createBuildProcess(context.getBuild(), context);

        buildProcess.start();
        buildProcess.waitFor();
    }
}

