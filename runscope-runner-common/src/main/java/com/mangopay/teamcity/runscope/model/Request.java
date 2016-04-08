package com.mangopay.teamcity.runscope.model;

public class Request {
    private int assertionsDefined;
    private int assertionsFailed;
    private int assertionsPassed;

    private RequestStatus result;

    private int scriptsDefined;
    private int scriptsFailed;
    private int scriptsPassed;

    private String url;

    private int variablesDefined;
    private int variablesFailed;
    private int variablesPassed;

    /*public int getAssertionsDefined() {
        return assertionsDefined;
    }

    @XmlElement(name="assertions_defined")
    public void setAssertionsDefined(int assertionsDefined) {
        this.assertionsDefined = assertionsDefined;
    }

    public int getAssertionsFailed() {
        return assertionsFailed;
    }

    @XmlElement(name="assertions_failed")
    public void setAssertionsFailed(int assertionsFailed) {
        this.assertionsFailed = assertionsFailed;
    }

    public int getAssertionsPassed() {
        return assertionsPassed;
    }

    @XmlElement(name="assertions_passed")
    public void setAssertionsPassed(int assertionsPassed) {
        this.assertionsPassed = assertionsPassed;
    }
*/
    public RequestStatus getResult() {
        return result;
    }

    public void setResult(RequestStatus result) {
        this.result = result;
    }
/*
    public int getScriptsDefined() {
        return scriptsDefined;
    }

    @XmlElement(name="scripts_defined")
    public void setScriptsDefined(int scriptsDefined) {
        this.scriptsDefined = scriptsDefined;
    }

    public int getScriptsFailed() {
        return scriptsFailed;
    }

    @XmlElement(name="scripts_failed")
    public void setScriptsFailed(int scriptsFailed) {
        this.scriptsFailed = scriptsFailed;
    }

    public int getScriptsPassed() {
        return scriptsPassed;
    }

    @XmlElement(name="scripts_passed")
    public void setScriptsPassed(int scriptsPassed) {
        this.scriptsPassed = scriptsPassed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVariablesDefined() {
        return variablesDefined;
    }

    @XmlElement(name="variables_defined")
    public void setVariablesDefined(int variablesDefined) {
        this.variablesDefined = variablesDefined;
    }

    public int getVariablesFailed() {
        return variablesFailed;
    }

    @XmlElement(name="variables_failed")
    public void setVariablesFailed(int variablesFailed) {
        this.variablesFailed = variablesFailed;
    }

    public int getVariablesPassed() {
        return variablesPassed;
    }

    @XmlElement(name="variables_passed")
    public void setVariablesPassed(int variablesPassed) {
        this.variablesPassed = variablesPassed;
    }*/
}
