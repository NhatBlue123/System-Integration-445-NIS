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
        <div class="content">
            <div class="module">
                <div class="module-head">
                    <h3>
                        All Members</h3>
                </div>
                <div class="module-option clearfix">
                    <form>
                        <div class="input-append pull-left">
                            <input type="text" class="span3" placeholder="Filter by name...">
                            <button type="submit" class="btn">
                                <i class="icon-search"></i>
                            </button>
                        </div>
                    </form>
                    <div class="btn-group pull-right" data-toggle="buttons-radio">
                        <button type="button" class="btn">
                            All</button>
                        <button type="button" class="btn">
                            Male</button>
                        <button type="button" class="btn">
                            Female</button>
                    </div>
                </div>
                <div class="module-body">

                    <core:forEach var="user" items="${listUsers}" varStatus="status" step="2">  
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="media user">
                                    <a class="media-avatar pull-left" href="#">
                                        <img src="${contextPath}/resources/images/user.png">
                                    </a>
                                    <div class="media-body">
                                        <h3 class="media-title">
                                            ${listUsers[status.index].userName}
                                            <!--${user.userName}-->
                                        </h3>
                                        <p>
                                            <small class="muted">  ${listUsers[status.index].email} </small></p>
                                        <div class="media-option btn-group shaded-icon">
                                            <button class="btn btn-small">
                                                <i class="icon-envelope"></i>
                                            </button>
                                            <button class="btn btn-small">
                                                <i class="icon-share-alt"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="media user">
                                    <a class="media-avatar pull-left" href="#">
                                        <img src="${contextPath}/resources/images/user.png">
                                    </a>
                                    <div class="media-body">
                                        <h3 class="media-title">
                                            ${listUsers[status.index+1].userName}</h3>
                                        <p>
                                            <small class="muted">${listUsers[status.index+1].email}</small></p>
                                        <div class="media-option btn-group shaded-icon">
                                            <button class="btn btn-small">
                                                <i class="icon-envelope"></i>
                                            </button>
                                            <button class="btn btn-small">
                                                <i class="icon-share-alt"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br />
                    </core:forEach>
                </div>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>