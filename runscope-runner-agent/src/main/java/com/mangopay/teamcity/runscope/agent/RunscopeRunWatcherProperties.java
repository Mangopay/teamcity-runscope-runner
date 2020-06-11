package com.mangopay.teamcity.runscope.agent;

import java.util.Properties;

import jetbrains.buildServer.agent.SimpleBuildLogger;

public class RunscopeRunWatcherProperties {

    public static final String NB_RETRY = "watcher.retry.number";
    public static final String RETRY_INTERVAL = "watcher.retry.interval";

    public static final long DEFAULT_RETRY_INTERVAL = 3000L;
    public static final int DEFAULT_MAX_RETRIES = 20;

    private long retryInterval = DEFAULT_RETRY_INTERVAL;
    private int maxRetries = DEFAULT_MAX_RETRIES;

    public RunscopeRunWatcherProperties(Properties properties, SimpleBuildLogger logger) {
        try {
            maxRetries = Integer.parseInt(properties.getProperty(NB_RETRY));
        } catch (NumberFormatException e) {
            logger.error("Issue parsing the property " + NB_RETRY + " default value will be used : " + maxRetries);
        }
        try {
            retryInterval = Long.parseLong(properties.getProperty(RETRY_INTERVAL));
        } catch (NumberFormatException e) {
            logger.error("Issue parsing the property " + RETRY_INTERVAL + " default value will be used : " + retryInterval);
        }
    }

    public long getRetryInterval() {
       return retryInterval;
    }

    public int getMaxRetries()
    {
        return maxRetries;
    }
}