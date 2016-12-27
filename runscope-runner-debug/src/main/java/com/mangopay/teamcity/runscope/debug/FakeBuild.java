package com.mangopay.teamcity.runscope.debug;

import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.artifacts.ArtifactDependencyInfo;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.util.Option;
import jetbrains.buildServer.vcs.VcsChangeInfo;
import jetbrains.buildServer.vcs.VcsRoot;
import jetbrains.buildServer.vcs.VcsRootEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
