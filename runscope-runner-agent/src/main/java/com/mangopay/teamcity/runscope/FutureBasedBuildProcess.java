package com.mangopay.teamcity.runscope;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.agent.BuildProcess;
import jetbrains.buildServer.agent.BuildProgressLogger;
import jetbrains.buildServer.agent.BuildRunnerContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

abstract class FutureBasedBuildProcess implements BuildProcess, Callable<BuildFinishedStatus>
{
    @NotNull
    protected final BuildProgressLogger logger;
    private Future<BuildFinishedStatus> myFuture;

    public FutureBasedBuildProcess(@NotNull final BuildRunnerContext context) {
        this.logger = context.getBuild().getBuildLogger();
    }

    public void start() throws RunBuildException
    {
        try {
            myFuture = Executors.newSingleThreadExecutor().submit(this);
        } catch (final RejectedExecutionException e) {
            logger.error("Failed to start build!");
            logger.exception(e);
            throw new RunBuildException(e);
        }
    }

    public boolean isInterrupted()
    {
        return myFuture.isCancelled() && isFinished();
    }

    public boolean isFinished()
    {
        return myFuture.isDone();
    }

    public void interrupt()
    {
        logger.message("Attempt to interrupt build process");
        myFuture.cancel(true);
    }

    @NotNull
    public BuildFinishedStatus waitFor() throws RunBuildException
    {
        try {
            final BuildFinishedStatus status = myFuture.get();
            return status;
        } catch (final InterruptedException e) {
            throw new RunBuildException(e);
        } catch (final ExecutionException e) {
            throw new RunBuildException(e);
        } catch (final CancellationException e) {
            logger.exception(e);
            return BuildFinishedStatus.INTERRUPTED;
        }
    }
}
