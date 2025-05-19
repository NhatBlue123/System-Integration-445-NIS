<%-- 
    Document   : editEmployee
    Created on : May 18, 2025, 11:54:09 PM
    Author     : bluez
--%>

<%-- 
    Document   : addEmployee
    Created on : May 18, 2025, 4:22:48 PM
    Author     : bluez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        Edit Employee
    </tiles:putAttribute>
</tiles:insertDefinition>

