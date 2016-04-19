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
    private Future<BuildFinishedStatus> future;

    protected FutureBasedBuildProcess(@NotNull final BuildRunnerContext context) {
        logger = context.getBuild().getBuildLogger();
    }

    @Override
    public void start() throws RunBuildException
    {
        try {
            future = Executors.newSingleThreadExecutor().submit(this);
        } catch (final RejectedExecutionException e) {
            logger.error("Failed to start build!");
            logger.exception(e);
            throw new RunBuildException(e);
        }
    }

    @Override
    public boolean isInterrupted()
    {
        return future.isCancelled() && isFinished();
    }

    @Override
    public boolean isFinished()
    {
        return future.isDone();
    }

    @Override
    public void interrupt()  {
        logger.message("Attempt to interrupt build process");
        future.cancel(true);
    }

    @Override
    @NotNull
    public BuildFinishedStatus waitFor() throws RunBuildException
    {
        try {
            return future.get();
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
