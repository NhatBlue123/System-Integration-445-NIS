<%-- 
    Document   : addUser
    Created on : Feb 6, 2015, 8:51:31 AM
    Author     : KunPC
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>
<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        <div class="content">
            <div class="module">
                <div class="module-head">
                    <h3>Create User</h3>
                </div>
                
                    <div class="module-body">
                        <form:form method="POST" action="user/add.html" commandName="user" cssClass="form-horizontal row-fluid">
                        <!--<form class="form-horizontal row-fluid">-->
                            <div class="control-group">
                                <label class="control-label" for="basicinput">UserName</label>
                                <div class="controls">
                                    <form:input class="span8" path="userName" id="basicinput" placeholder="Type something here..."/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="basicinput">Password</label>
                                <div class="controls">
                                    <form:password class="span8" path="password" id="basicinput"  placeholder="Type something here..."/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label" for="basicinput">Email</label>
                                <div class="controls">
                                    <form:password class="span8" path="email" id="basicinput" placeholder="Type something here..."/>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">Active</label>
                                <div class="controls">
                                    <label class="checkbox inline">
                                        <input type="checkbox" value="">
                                    </label>
                                </div>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <button type="submit" class="btn">Submit Form</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
            </div>
        </div><!--/.content-->
    </tiles:putAttribute>
</tiles:insertDefinition>