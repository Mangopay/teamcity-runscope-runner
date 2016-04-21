
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.mangopay.teamcity.runscope.server.RunscopeConstantsBean"/>

<l:settingsGroup title="Authentication">
    <tr>
        <th><label for="${constants.tokenKey}">Token:<l:star/></label></th>
        <td><props:passwordProperty name="${constants.tokenKey}" className="longField" />
            <span class="error" id="error_${constants.tokenKey}"></span>
            <span class="smallNote">
                Specify Runscope Authorization token. You can get this from your user page in the Runscope web portal.
            </span>
        </td>
    </tr>
</l:settingsGroup>

<l:settingsGroup title="Test configuration">
    <tr>
        <th><label for="${constants.bucketKey}">Bucket:<l:star/></label></th>
        <td><props:textProperty name="${constants.bucketKey}" className="longField" />
            <span class="error" id="error_${constants.bucketKey}"></span>
            <span class="smallNote">
                Specify Runscope bucket key.
            </span>
        </td>
    </tr>

    <tr class="advancedSetting">
        <th><label for="${constants.testsKey}">Tests:</label></th>
        <td><props:multilineProperty name="${constants.testsKey}" linkTitle="Edit tests include list" cols="49" rows="3" className="longField" />
            <span class="error" id="error_${constants.testsKey}"></span>
            <span class="smallNote">
                Enter comma- or newline-separated Runscope test keys. If left empty, all tests from the bucket will be ran.
            </span>
        </td>
    </tr>

    <tr>
        <th><label for="${constants.environmentKey}">Environment:</label></th>
        <td><props:textProperty name="${constants.environmentKey}" className="longField" />
            <span class="error" id="error_${constants.environmentKey}"></span>
            <span class="smallNote">
                Specify Runscope Environment. If left empty, default environment will be used.
            </span>
        </td>
    </tr>

    <tr class="advancedSetting">
        <th><label for="${constants.excludedTestsKey}">Excluded tests:</label></th>
        <td><props:multilineProperty name="${constants.excludedTestsKey}" linkTitle="Edit tests exclude list" cols="49" rows="3" className="longField" />
            <span class="error" id="error_${constants.excludedTestsKey}"></span>
            <span class="smallNote">
                Enter comma- or newline-separated Runscope test keys.
            </span>
        </td>
    </tr>

    <tr class="advancedSetting">
        <th><label for="${constants.variablesKey}">Initial variables:</label></th>
        <td><props:multilineProperty name="${constants.variablesKey}" linkTitle="Edit initial variables" cols="49" rows="5" className="longField" />
            <span class="error" id="error_${constants.variablesKey}"></span>
            <span class="smallNote">
                Newline-separated Runscope variables.<br/>
                Example: <kbd>variable=value</kbd><br/>
                <kbd>=value</kbd> is optional. If omitted, the corresponding build parameter (${constants.runscopeVariablesPrefix}*) value will be used.<br/>
            </span>
        </td>
    </tr>

    <c:set var='onclick'>
        $('${constants.parallelCountKey}').disabled = !this.checked;
    </c:set>
    <tr>
        <th><label for="${constants.parallelCountKey}">Parallelization:</label></th>
        <td>
            <props:checkboxProperty name="${constants.parallelKey}" onclick='${onclick}'/>
            <label for="${constants.parallelKey}">Run n tests simultaneously:</label>
            <props:textProperty name="${constants.parallelCountKey}" disabled="${'true' != propertiesBean.properties[constants.parallelKey]}"/>
            <span class="error" id="error_${constants.parallelCountKey}"></span>
            <span class="smallNote">If disabled, tests will be triggered sequentially.</span>
        </td>
    </tr>
</l:settingsGroup>
