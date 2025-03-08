<%-- 
    Document   : listEmployee
    Created on : Feb 12, 2015, 9:12:18 AM
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
                    <h3>Employee</h3>
                </div>
                <div class="module-body table">
                    <table cellpadding="0" cellspacing="0" border="0" class="datatable-1 table table-bordered table-striped	 display" width="100%">
                        <thead>
                            <tr>
                                <th>Employee Number</th>
                                <th>Full Name</th>
                                <th>SSN</th>
                                <th>Pay Rate</th>
                                <th>Vacation_Days</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach var="employee" items="${listEmployees}">  
                                <tr class="even gradeX">
                                    <td>${employee.employeeNumber}</td>
                                    <td>${employee.lastName} ${employee.firstName}</td>
                                    <td>${employee.ssn}</td>
                                    <td class="center">${employee.payRate}</td>
                                    <td class="center">${employee.vacationDays}</td>
                                </tr>
                            </core:forEach>
                        </tbody>
                    </table>
                </div>
            </div><!--/.module-->
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>
