package com.mangopay.teamcity.runscope.agent;


import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.artifacts.ArtifactDependencyInfo;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.util.Option;
import jetbrains.buildServer.vcs.VcsChangeInfo;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.List;

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

class FakeContext implements BuildRunnerContext {

    private final Map<String, String> runnerParameters;
    private final AgentRunningBuild agentRunningBuild;

    public FakeContext(final String token, final String bucket, final String tests, final String excludedTests, final String environment) {
        runnerParameters = new HashMap<String, String>() {
            {
                put(RunscopeConstants.SETTINGS_TOKEN, token);
                put(RunscopeConstants.SETTINGS_BUCKET, bucket);
                put(RunscopeConstants.SETTINGS_TESTS, tests);
                put(RunscopeConstants.SETTINGS_EXCLUDED_TESTS, excludedTests);
                put(RunscopeConstants.SETTINGS_ENVIRONMENT, environment);
            }
        };

        agentRunningBuild = new FakeBuild();
    }

    @Override
    public String getId() {
        return null;
    }

    @NotNull
    @Override
    public AgentRunningBuild getBuild() {
        return agentRunningBuild;
    }

    @NotNull
    @Override
    public File getWorkingDirectory() {
        return null;
    }

    @Override
    public String getRunType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @NotNull
    @Override
    public BuildParametersMap getBuildParameters() {
        return null;
    }

    @Override
    public Map<String, String> getConfigParameters() {
        return null;
    }

    @NotNull
    @Override
    public Map<String, String> getRunnerParameters() {
        return runnerParameters;
    }

    @Override
    public void addSystemProperty(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addEnvironmentVariable(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addConfigParameter(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addRunnerParameter(@NotNull String key, @NotNull String value) {

    }

    @NotNull
    @Override
    public ValueResolver getParametersResolver() {
        return null;
    }

    @NotNull
    @Override
    public String getToolPath(@NotNull String toolName) throws ToolCannotBeFoundException {
        return null;
    }

    @Override
    public boolean parametersHaveReferencesTo(@NotNull Collection<String> keys) {
        return false;
    }
}

class FakeBuild implements AgentRunningBuild {

    private final BuildProgressLogger logger;

    public FakeBuild() {
        logger = new SystemOutLogger();
    }

    @Override
    public BuildParametersMap getMandatoryBuildParameters() {
        return null;
    }

    @NotNull
    @Override
    public File getCheckoutDirectory() {
        return null;
    }

    @Override
    public File getWorkingDirectory() {
        return null;
    }

    @Nullable
    @Override
    public String getArtifactsPaths() {
        return null;
    }

    @Override
    public boolean getFailBuildOnExitCode() {
        return false;
    }

    @Override
    public ResolvedParameters getResolvedParameters() {
        return null;
    }

    @Override
    public String getRunType() {
        return null;
    }

    @Override
    public UnresolvedParameters getUnresolvedParameters() {
        return null;
    }

    @Override
    public BuildParametersMap getBuildParameters() {
        return null;
    }

    @NotNull
    @Override
    public Map<String, String> getRunnerParameters() {
        return null;
    }

    @Override
    public String getBuildNumber() {
        return null;
    }

    @Override
    public Map<String, String> getSharedConfigParameters() {
        return null;
    }

    @Override
    public void addSharedConfigParameter(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addSharedSystemProperty(@NotNull String key, @NotNull String value) {

    }

    @Override
    public void addSharedEnvironmentVariable(@NotNull String key, @NotNull String value) {

    }

    @Override
    public BuildParametersMap getSharedBuildParameters() {
        return null;
    }

    @Override
    public ValueResolver getSharedParametersResolver() {
        return null;
    }

    @Override
    public Collection<AgentBuildFeature> getBuildFeatures() {
        return null;
    }

    @Override
    public Collection<AgentBuildFeature> getBuildFeaturesOfType(@NotNull String type) {
        return null;
    }

    @Override
    public void stopBuild(@NotNull String reason) {

    }

    @Nullable
    @Override
    public BuildInterruptReason getInterruptReason() {
        return null;
    }

    @Override
    public boolean isBuildFailingOnServer() throws InterruptedException {
        return false;
    }

    @Override
    public boolean isInAlwaysExecutingStage() {
        return false;
    }

    @Override
    public String getProjectName() {
        return null;
    }

    @Override
    public String getBuildTypeId() {
        return null;
    }

    @Override
    public String getBuildTypeExternalId() {
        return null;
    }

    @Override
    public String getBuildTypeName() {
        return null;
    }

    @Override
    public long getBuildId() {
        return 0;
    }

    @Override
    public boolean isCleanBuild() {
        return false;
    }

    @Override
    public boolean isPersonal() {
        return false;
    }

    @Override
    public boolean isPersonalPatchAvailable() {
        return false;
    }

    @Override
    public boolean isCheckoutOnAgent() {
        return false;
    }

    @Override
    public boolean isCheckoutOnServer() {
        return false;
    }

    @Override
    public long getExecutionTimeoutMinutes() {
        return 0;
    }

    @Override
    public List<ArtifactDependencyInfo> getArtifactDependencies() {
        return null;
    }

    @Override
    public String getAccessUser() {
        return null;
    }

    @NotNull
    @Override
    public String getAccessCode() {
        return null;
    }

    @Override
    public List<VcsRootEntry> getVcsRootEntries() {
        return null;
    }

    @Override
    public String getBuildCurrentVersion(@NotNull VcsRoot vcsRoot) {
        return null;
    }

    @Override
    public String getBuildPreviousVersion(@NotNull VcsRoot vcsRoot) {
        return null;
    }

    @Override
    public boolean isCustomCheckoutDirectory() {
        return false;
    }

    @Override
    public List<VcsChangeInfo> getVcsChanges() {
        return null;
    }

    @Override
    public List<VcsChangeInfo> getPersonalVcsChanges() {
        return null;
    }

    @NotNull
    @Override
    public File getBuildTempDirectory() {
        return null;
    }

    @Override
    public File getAgentTempDirectory() {
        return null;
    }

    @Override
    public BuildProgressLogger getBuildLogger() {
        return logger;
    }

    @Override
    public BuildAgentConfiguration getAgentConfiguration() {
        return null;
    }

    @Override
    public <T> T getBuildTypeOptionValue(@NotNull Option<T> option) {
        return null;
    }

    @Override
    public File getDefaultCheckoutDirectory() {
        return null;
    }
}

class SystemOutLogger implements FlowLogger {

    private String flow;
    private static int index;

    public SystemOutLogger() {

    }

    public SystemOutLogger(String flow) {
        if(flow != null && !flow.isEmpty()) {
            this.flow = '[' + flow + ']';
        }
    }

    private void log(String cateory, Object... args) {
        StringBuilder sb = new StringBuilder();
        if(flow != null && !flow.isEmpty()) sb.append(flow);

        sb.append(cateory);
        for(Object arg : args) {
            sb.append("\n\t");
            sb.append(arg);
        }

        System.out.println(sb.toString());
    }

    public void activityStarted(String s, String s1) {
        log("activityStarted", s, s1);
    }


    public void activityStarted(String s, String s1, String s2) {
        log("activityStarted", s, s1, s2);
    }


    public void activityFinished(String s, String s1) {
        log("activityFinished", s, s1);
    }


    public void targetStarted(String s) {
        log("targetStarted", s);

    }


    public void targetFinished(String s) {
        log("targetFinished", s);
    }


    public void buildFailureDescription(String s) {
        log("buildFailureDescription", s);
    }


    public void internalError(String s, String s1, Throwable throwable) {
        log("internalError", s, s1, throwable);
    }


    public void progressStarted(String s) {
        log("progressStarted", s);
    }


    public void progressFinished() {
        log("progressFinished");
    }

    @Override
    public void logMessage(BuildMessage1 buildMessage1) {
        log("logMessage", buildMessage1);
    }


    public void logTestStarted(String s) {
        log("logTestStarted", s);
    }


    public void logTestStarted(String s, Date date) {
        log("logTestStarted", s, date);
    }


    public void logTestFinished(String s) {
        log("logTestFinished", s);
    }


    public void logTestFinished(String s, Date date) {
        log("logTestFinished", s, date);
    }


    public void logTestIgnored(String s, String s1) {
        log("logTestIgnored", s, s1);
    }


    public void logSuiteStarted(String s) {
        log("logSuiteStarted", s);
    }


    public void logSuiteStarted(String s, Date date) {
        log("logSuiteStarted", s, date);
    }


    public void logSuiteFinished(String s) {
        log("logSuiteFinished", s);
    }


    public void logSuiteFinished(String s, Date date) {
        log("logSuiteFinished", s, date);
    }


    public void logTestStdOut(String s, String s1) {
        log("logTestStdOut", s, s1);
    }


    public void logTestStdErr(String s, String s1) {
        log("logTestStdErr", s, s1);
    }


    public void logTestFailed(String s, Throwable throwable) {
        log("logTestFailed", s, throwable);
    }


    public void logComparisonFailure(String s, Throwable throwable, String s1, String s2) {
        log("logComparisonFailure", s, throwable, s1, s2);
    }


    public void logTestFailed(String s, String s1, String s2) {
        log("logTestFailed", s, s1, s2);
    }


    public void flush() {

    }


    public void ignoreServiceMessages(Runnable runnable) {

    }

    @Override
    public FlowLogger getFlowLogger(String s) {
        Integer thisIndex = index++;
        return new SystemOutLogger(thisIndex.toString());


        //return new SystemOutLogger(s);

    }

    @Override
    public FlowLogger getThreadLogger() {
        return this;
    }

    public String getFlowId() {
        return null;
    }

    @Override
    public void logBuildProblem(BuildProblemData buildProblemData) {

    }

    public void message(String s) {
        log("message", s);
    }


    public void error(String s) {
        log("error", s);
    }


    public void warning(String s) {
        log("warning", s);
    }


    public void exception(Throwable throwable) {
        log("exception", throwable);
    }


    public void progressMessage(String s) {
        log("progressMessage", s);
    }

    @Override
    public void startFlow() {
        log("startFlow");
    }

    @Override
    public void disposeFlow() {
        log("disposeFlow");
    }
}
