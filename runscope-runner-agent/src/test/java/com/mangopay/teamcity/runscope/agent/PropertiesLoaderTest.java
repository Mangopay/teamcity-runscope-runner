package com.mangopay.teamcity.runscope.agent;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropertiesLoaderTest {
    
    @Test
    public void loadPropertiesShouldRetrieveNbRetryValue() throws Exception {
        PropertiesLoader loader = new PropertiesLoader("config.properties", new FakeLogger());

        Properties prop = loader.load();

        assertEquals("465", prop.getProperty(RunscopeRunWatcherProperties.NB_RETRY));
    }

    @Test
    public void loadPropertiesShouldRetrieveRetryIntervalValue() throws Exception {
        PropertiesLoader loader = new PropertiesLoader("config.properties", new FakeLogger());

        Properties prop = loader.load();

        assertEquals("3251", prop.getProperty(RunscopeRunWatcherProperties.RETRY_INTERVAL));
    }
}