package com.mangopay.teamcity.runscope.agent;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.common.RunscopeConstants;
import com.mangopay.teamcity.runscope.agent.model.*;
import jetbrains.buildServer.agent.BuildProgressLogger;

class RequestLogger {

    private final Run run;
    private final BuildProgressLogger logger;
    private final AssertionFormatter assertionFormatter;

    public RequestLogger(final Run run, final BuildProgressLogger logger) {
        this.run = run;
        this.logger = logger;
        assertionFormatter = new AssertionFormatter();

    }

    public String log(final Step step, final Request request) {
        final StringBuilder sb = new StringBuilder();
        boolean failed = false;

        for(final RequestAssertion assertion : request.getAssertions()) {
            final String assertionLog = assertionFormatter.format(assertion);
            logger.message(assertionLog);

            if(assertion.getResult() != BinaryStatus.PASSED) {
                failed = true;
                sb.append(assertionLog);
                sb.append('\n');
            }
        }

        if(failed) {
            sb.append(String.format(RunscopeConstants.LOG_SEE_FULL_LOG, run.getUrl()));
            if (!StringUtil.isEmptyOrSpaces(step.getId())) {
                sb.append('#');
                sb.append(step.getId());
            }
        }

        return sb.toString();
    }

    public static String getName(final Step step) {
        final StringBuilder sb = new StringBuilder();

        if(StepType.PAUSE == step.getStepType()) {
            sb.append("Pause ");
            sb.append(step.getDuration());
            sb.append(" second(s)");
        }
        else if(StepType.GHOST_INSPECTOR == step.getStepType()) {
            sb.append("[Ghost Inspector] ");
            sb.append(step.getTestName());
        }
        else if(StepType.INCOMING == step.getStepType()) {
            sb.append("Incoming request");
        }
        else {
            sb.append(step.getNote());
        }

        return sb.toString();
    }
}

