package com.mangopay.teamcity.runscope.agent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jetbrains.buildServer.agent.SimpleBuildLogger;

class PropertiesLoader {

    private SimpleBuildLogger logger;
    
    private final String filename;

    public PropertiesLoader(final String filename, SimpleBuildLogger logger) {
        this.filename = filename;
        this.logger = logger;
    }

    public Properties load() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                logger.error("cannot find the file " + filename);
                return prop;
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.exception(ex);
        }
        return prop;
    }
}