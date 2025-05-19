<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">

        <c:set var="contextPath" value="${pageContext.request.contextPath}" />

        <div class="content">
            <div class="module">
                <div class="module-head">
                    <h3>Employee Management</h3>
                </div>
                <div class="module-body">
                    <div class="search-container" style="margin-bottom: 20px;">
                        <input type="text" id="search-input" placeholder="Search in all columns..."
                               style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div id="employee-table"></div>
                </div>
            </div>
        </div>

        <!-- Tabulator & STOMP CDN -->
        <link href="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
            const employeeData = [
            <c:forEach var="item" items="${listEmployees}" varStatus="loop">
            {
            idEmployee: ${item.idEmployee},
                    employeeNumber: "${item.employeeNumber}",
                    lastName: "${item.lastName}",
                    firstName: "${item.firstName}",
                    ssn: "${item.ssn}",
                    payRate: "${item.payRate}",
                    payRatesId: "${item.payRatesId}",
                    vacationDays: "${item.vacationDays}",
                    paidToDate: "${item.paidToDate}",
                    paidLastYear: "${item.paidLastYear}"
            }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
                    const table = new Tabulator("#employee-table", {
                    data: employeeData,
                            layout: "fitDataStretch",
                            pagination: "local",
                            paginationSize: 10,
                            movableColumns: true,
                            columns: [
                            {title: "ID", field: "idEmployee", headerFilter: "input"},
                            {title: "Employee Number", field: "employeeNumber", headerFilter: "input"},
                            {title: "Last Name", field: "lastName", headerFilter: "input"},
                            {title: "First Name", field: "firstName", headerFilter: "input"},
                            {title: "SSN", field: "ssn", headerFilter: "input"},
                            {title: "Pay Rate", field: "payRate", headerFilter: "input"},
                            {title: "Pay Rate ID", field: "payRatesId", headerFilter: "input"},
                            {
                            title: "Actions",
                                    field: "idEmployee",
                                    hozAlign: "center",
                                    formatter: function (cell) {
                                    const id = cell.getValue();
                                            return '<a href="http://localhost:8080/springapp/admin/employee/editEmployee/' + id + '" ' +
                                            'class="btn btn-warning btn-sm" style="margin-right: 5px;">Edit</a>' +
                                            '<button class="btn btn-danger btn-sm" onclick="deleteEmployee(' + id + ')">Delete</button>';
                                    },
                                    width: 200
                            }
                            ]
                    });
                    document.getElementById("search-input").addEventListener("input", function (e) {
            const term = e.target.value.toLowerCase();
                    table.setFilter(function (data) {
                    return Object.values(data).some(value =>
                            value && value.toString().toLowerCase().includes(term)
                            );
                    });
            });
                    window.deleteEmployee = function(id) {
                    if (confirm('Are you sure you want to delete Employee ID ' + id + '?')) {
                    fetch('http://localhost:8080/springapp/admin/employee/deleteEmployeeById/' + id, {
                    method: 'DELETE'
                    })
                            .then(response => {
                            if (response.ok) {
                            alert('Deleted employee ' + id);
                                  //  location.reload();
                            } else {
                            alert('Failed to delete employee ' + id);
                            }
                            })
                            .catch(error => {
                            console.error('Error:', error);
                                    alert('Error deleting employee ' + id);
                            });
                    }
                    };
                    const socket = new SockJS('${contextPath}/ws');
                    const stompClient = Stomp.over(socket);
                    stompClient.debug = null;
                    stompClient.connect({}, function (frame) {
                    console.log('Connected: ' + frame);
                            stompClient.subscribe('/topic/employee', function (message) {
                            const data = JSON.parse(message.body);
                                    table.setData(data);
                                    console.table(data);
                            });
                    }, function (error) {
                    console.error('STOMP error: ' + error);
                    });
            });
        </script>

    </tiles:putAttribute>
</tiles:insertDefinition>
