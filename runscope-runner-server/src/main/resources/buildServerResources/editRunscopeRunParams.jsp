
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.mangopay.teamcity.runscope.RunscopeConstantsBean"/>

<l:settingsGroup title="Runscope authentication">
    <tr>
        <th><label>API key:</label></th>
        <td><props:passwordProperty name="${constants.apiKey}" className="longField" />
            <span class="error" id="error_${constants.apiKey}"></span>
            <span class="smallNote">
                Specify Runscope API key. You can get this from your user page in the Runscope web portal.
            </span>
        </td>
    </tr>
</l:settingsGroup>

<l:settingsGroup title="Test configuration">
    <tr>
        <th><label>Bucket:</label></th>
        <td><props:textProperty name="${constants.bucketKey}" className="longField" />
            <span class="error" id="error_${constants.bucketKey}"></span>
            <span class="smallNote">
                Specify Runscope Bucket key.
            </span>
        </td>
    </tr>

    <tr>
        <th><label>Test:</label></th>
        <td><props:multilineProperty name="${constants.testsKey}" linkTitle="Edit Runscope tests list" cols="49" rows="3" className="longField" />
            <span class="error" id="error_${constants.testsKey}"></span>
            <span class="smallNote">
                Enter comma- or newline-separated Runscope Test keys. If left empty, all tests from the bucket will be ran.
            </span>
        </td>
    </tr>

    <tr>
        <th><label>Environment:</label></th>
        <td><props:textProperty name="${constants.environmentKey}" className="longField" />
            <span class="error" id="error_${constants.environmentKey}"></span>
            <span class="smallNote">
                Specify Runscope Environment. If left empty, default environment will be used.
            </span>
        </td>
    </tr>

    <tr>
        <th><label>Initial variables:</label></th>
        <td><props:multilineProperty name="${constants.variablesKey}" linkTitle="Edit initial variables" cols="49" rows="5" className="longField" />
            <span class="error" id="error_${constants.variablesKey}"></span>
            <span class="smallNote">
                Newline-separated Runscope variables.<br/>
                Example: <kbd>variable=value</kbd><br/>
                <kbd>=value</kbd> is optional. If omitted, the corresponding build parameter (${constants.runscopeVariablesPrefix}*) value will be used.<br/>
            </span>
        </td>
    </tr>
</l:settingsGroup>