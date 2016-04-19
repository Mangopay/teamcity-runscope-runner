package com.mangopay.teamcity.runscope.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Request {
    private Integer assertionsDefined;
    private Integer assertionsFailed;
    private Integer assertionsPassed;

    private RequestStatus result;

    private Integer scriptsDefined;
    private Integer scriptsFailed;
    private Integer scriptsPassed;

    private String url;

    private Integer variablesDefined;
    private Integer variablesFailed;
    private Integer variablesPassed;

    private List<RequestAssertion> assertions;
    private List<RequestVariable> variables;

    public Integer getAssertionsDefined() {
        return assertionsDefined;
    }

	@JsonProperty("assertions_defined")
    public void setAssertionsDefined(final Integer assertionsDefined) {
        this.assertionsDefined = assertionsDefined;
    }

    public Integer getAssertionsFailed() {
        return assertionsFailed;
    }

	@JsonProperty("assertions_failed")
    public void setAssertionsFailed(final Integer assertionsFailed) {
        this.assertionsFailed = assertionsFailed;
    }

    public Integer getAssertionsPassed() {
        return assertionsPassed;
    }

	@JsonProperty("assertions_passed")
    public void setAssertionsPassed(final Integer assertionsPassed) {
        this.assertionsPassed = assertionsPassed;
    }

    public RequestStatus getResult() {
        return result;
    }

    public void setResult(final RequestStatus result) {
        this.result = result;
    }

    public Integer getScriptsDefined() {
        return scriptsDefined;
    }

	@JsonProperty("scripts_defined")
    public void setScriptsDefined(final Integer scriptsDefined) {
        this.scriptsDefined = scriptsDefined;
    }

    public Integer getScriptsFailed() {
        return scriptsFailed;
    }

	@JsonProperty("scripts_failed")
    public void setScriptsFailed(final Integer scriptsFailed) {
        this.scriptsFailed = scriptsFailed;
    }

    public Integer getScriptsPassed() {
        return scriptsPassed;
    }

	@JsonProperty("scripts_passed")
    public void setScriptsPassed(final Integer scriptsPassed) {
        this.scriptsPassed = scriptsPassed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Integer getVariablesDefined() {
        return variablesDefined;
    }

	@JsonProperty("variables_defined")
    public void setVariablesDefined(final Integer variablesDefined) {
        this.variablesDefined = variablesDefined;
    }

    public Integer getVariablesFailed() {
        return variablesFailed;
    }

	@JsonProperty("variables_failed")
    public void setVariablesFailed(final Integer variablesFailed) {
        this.variablesFailed = variablesFailed;
    }

    public Integer getVariablesPassed() {
        return variablesPassed;
    }

	@JsonProperty("variables_passed")
    public void setVariablesPassed(final Integer variablesPassed) {
        this.variablesPassed = variablesPassed;
    }

    public List<RequestAssertion> getAssertions() {
        return assertions;
    }

    public void setAssertions(final List<RequestAssertion> assertions) {
        this.assertions = assertions;
    }

    public List<RequestVariable> getVariables() {
        return variables;
    }

    public void setVariables(final List<RequestVariable> variables) {
        this.variables = variables;
    }
}
