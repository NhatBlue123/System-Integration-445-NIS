<%-- 
    Document   : user
    Created on : Feb 4, 2015, 2:29:49 PM
    Author     : KunPC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>
<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        <h3>Error </h3>

    </tiles:putAttribute>
</tiles:insertDefinition>