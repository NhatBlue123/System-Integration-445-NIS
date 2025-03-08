<%-- 
    Document   : listPayRate
    Created on : Feb 24, 2015, 9:54:38 PM
    Author     : AnhDao
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
                    <h3>Pay Rates</h3>
                </div>
                <div class="module-body table">
                    <table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped	 display" width="100%">
                        <thead>
                            <tr>
                                <th>Pay Rate Name</th>
                                <th>Value</th>
                                <th>Tax Percentage</th>
                                <th>Pay Amount</th>
                                <th>PT LevelC</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach var="payrates" items="${listPayRates}">  
                                <tr class="even gradeX">
                                    <td>${payrates.payRateName}</td>
                                    <td>${payrates.value}</td>
                                    <td>${payrates.taxPercentage}</td>
                                    <td class="center">${payrates.payAmount}</td>
                                    <td class="center">${payrates.ptLevelC}</td>
                                </tr>
                            </core:forEach>
                        </tbody>
                    </table>
                </div>
            </div><!--/.module-->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
