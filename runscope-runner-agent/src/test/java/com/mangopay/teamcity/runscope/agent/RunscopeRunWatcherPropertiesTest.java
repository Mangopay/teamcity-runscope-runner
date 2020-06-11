package com.mangopay.teamcity.runscope.agent;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class RunscopeRunWatcherPropertiesTest {

    @Test
    public void maxRetryShouldReturnValueInProperties() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(RunscopeRunWatcherProperties.NB_RETRY, "12");

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(12, properties.getMaxRetries());
    }

    @Test
    public void maxRetryShouldReturnDefaultValueIfNothingInProperties() throws Exception {
        Properties prop = new Properties();

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(RunscopeRunWatcherProperties.DEFAULT_MAX_RETRIES, properties.getMaxRetries());
    }

    @Test
    public void maxRetryShouldReturnDefaultValueIfWrongValueInProperties() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(RunscopeRunWatcherProperties.NB_RETRY, "notaninteger");

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(RunscopeRunWatcherProperties.DEFAULT_MAX_RETRIES, properties.getMaxRetries());
    }

    @Test
    public void retryIntervalShouldReturnValueInProperties() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(RunscopeRunWatcherProperties.RETRY_INTERVAL, "5000");

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(5000L, properties.getRetryInterval());
    }

    @Test
    public void retryIntervalShouldReturnDefaultValueIfNothingInProperties() throws Exception {
        Properties prop = new Properties();

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(RunscopeRunWatcherProperties.DEFAULT_RETRY_INTERVAL, properties.getRetryInterval());
    }

    @Test
    public void retryIntervalShouldReturnDefaultValueIfWrongValueInProperties() throws Exception {
        Properties prop = new Properties();
        prop.setProperty(RunscopeRunWatcherProperties.RETRY_INTERVAL, "notalong");

        RunscopeRunWatcherProperties properties = new RunscopeRunWatcherProperties(prop, new FakeLogger());

        assertEquals(RunscopeRunWatcherProperties.DEFAULT_RETRY_INTERVAL, properties.getRetryInterval());
    }
}