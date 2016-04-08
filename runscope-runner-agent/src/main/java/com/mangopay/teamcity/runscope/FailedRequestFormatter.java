package com.mangopay.teamcity.runscope;

import com.mangopay.teamcity.runscope.model.Request;
import com.mangopay.teamcity.runscope.model.Run;
import com.mangopay.teamcity.runscope.model.Step;

public class FailedRequestFormatter {

    private final Run run;

    public FailedRequestFormatter(Run run) {
        this.run = run;
    }

    public String Format(final Step step, final Request request) {
        return "";
    }
}
