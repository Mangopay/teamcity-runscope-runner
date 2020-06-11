package com.mangopay.teamcity.runscope.agent;

import jetbrains.buildServer.agent.SimpleBuildLogger;

public class FakeLogger implements SimpleBuildLogger {

    @Override
    public void message(String message) {
    }

    @Override
    public void error(String message) {
    }

    @Override
    public void warning(String message) {
    }

    @Override
    public void exception(Throwable th) {
    }

    @Override
    public void progressMessage(String message) {
    }
}