<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.mangopay.teamcity.runscope.server.RunscopeConstantsBean"/>

<div class="parameter">
    Bucket: <strong><props:displayValue name="${constants.bucketKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Tests: <strong><props:displayValue name="${constants.testsKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Excluded tests: <strong><props:displayValue name="${constants.excludedTestsKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Environment: <strong><props:displayValue name="${constants.environmentKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Initial variables: <strong><props:displayValue name="${constants.variablesKey}" emptyValue="not specified"/></strong>
</div>

<c:if test='${!propertiesBean.properties[constants.parallelKey]}'>
    <div class="parameter">
        Run tests in parallel: <strong><props:displayCheckboxValue name="${constants.parallelKey}"/></strong>
    </div>
</c:if>

<c:if test='${propertiesBean.properties[constants.parallelKey]}'>
    <div class="parameter">
        Run tests in parallel: <strong><props:displayValue name="${constants.parallelCountKey}"/></strong>
    </div>
</c:if>

