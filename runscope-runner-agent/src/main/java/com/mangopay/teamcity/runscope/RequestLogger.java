package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.model.*;
import jetbrains.buildServer.agent.BuildProgressLogger;

import java.util.Map;

class RequestLogger {

    private final Run run;
    private final BuildProgressLogger logger;
    private final AssertionFormatter assertionFormatter;

    public RequestLogger(final Run run, final BuildProgressLogger logger) {
        this.run = run;
        this.logger = logger;
        this.assertionFormatter = new AssertionFormatter();

    }

    public String log(final Step step, final Request request) {
        StringBuilder sb = new StringBuilder();
        boolean failed = false;

        for(RequestAssertion assertion : request.getAssertions()) {
            String assertionLog = assertionFormatter.format(assertion);
            logger.message(assertionLog);

            if(assertion.getResult() != AssertionStatus.PASSED) {
                failed = true;
                sb.append(assertionLog);
                sb.append("\n");
            }
        }

        if(failed) {
            sb.append(String.format("See full log at : %s", run.getUrl()));
            if (!StringUtil.isEmptyOrSpaces(step.getId())) {
                sb.append('#');
                sb.append(step.getId());
            }
        }

        return sb.toString();
    }

    public String getName(final Step step) {
        StringBuilder sb = new StringBuilder();

        if("pause".equals(step.getStepType())) {
            sb.append("Pause ");
            sb.append(step.getDuration());
            sb.append(" second(s)");
        }
        else if("ghost-inspector".equals(step.getStepType())) {
            sb.append("[Ghost Inspector] ");
            sb.append(step.getTestName());
        }
        else {
            sb.append(step.getNote());
        }

        return sb.toString();
    }
}

