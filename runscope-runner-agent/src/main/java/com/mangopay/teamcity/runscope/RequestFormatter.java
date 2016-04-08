package com.mangopay.teamcity.runscope;

import com.intellij.openapi.util.text.StringUtil;
import com.mangopay.teamcity.runscope.model.*;

import java.util.Map;

class RequestFormatter {

    private final Run run;

    public RequestFormatter(final Run run) {
        this.run = run;
    }

    public String getOutput(final Step step, final Request request) {
        StringBuilder sb = new StringBuilder();
        AssertionFormatter assertionFormatter = new AssertionFormatter();

        for(RequestAssertion assertion : request.getAssertions()) {
            assertionFormatter.format(assertion, sb);
            sb.append("\n");
        }

        sb.append(String.format("See full log at : %s", run.getUrl()));
        if(!StringUtil.isEmptyOrSpaces(step.getId())) {
            sb.append('#');
            sb.append(step.getId());
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

