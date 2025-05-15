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
                                    <td>${employee.firstName} ${employee.lastName}</td>
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
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

        <script type="text/javascript">
            let stompClient = null;

            function connectWebSocket() {
                const socket = new SockJS("${contextPath}/ws"); 
                stompClient = Stomp.over(socket);
                stompClient.debug = null;
                stompClient.connect({}, function (frame) {
                    console.log('Connected: ' + frame);

                    stompClient.subscribe('/topic/employee', function (message) {
                        const employeeList = JSON.parse(message.body);
                        console.log('OK です ' + frame);

                        updateEmployeeTable(employeeList);
                    });
                });
            }

            function updateEmployeeTable(employeeList) {
                const tbody = document.querySelector("table tbody");
                tbody.innerHTML = ''; 

                employeeList.forEach(emp => {
                    const row = `
                        <tr>
                            <td>${emp.employeeNumber}</td>
                            <td>${emp.firstName} ${emp.lastName}</td>
                            <td>${emp.ssn}</td>
                            <td>${emp.payRate}</td>
                            <td>${emp.vacationDays}</td>
                        </tr>
                    `;
                    tbody.insertAdjacentHTML('beforeend', row);
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
                connectWebSocket();
            });
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>
