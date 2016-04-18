<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="constants" class="com.mangopay.teamcity.runscope.RunscopeConstantsBean"/>

<div class="parameter">
    Bucket: <strong><props:displayValue name="${constants.bucketKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Test: <strong><props:displayValue name="${constants.testsKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
    Environment: <strong><props:displayValue name="${constants.environmentKey}" emptyValue="not specified"/></strong>
</div>

<div class="parameter">
  Initial variables: <strong><props:displayValue name="${constants.variablesKey}"/></strong>
</div>
