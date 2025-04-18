<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        <div class="content">
            <div class="module">
                <div class="module-head">
                    <h3>EPerson Management</h3>
                </div>
                <div class="module-body">
                    <div class="search-container" style="margin-bottom: 20px;">
                        <input type="text" id="search-input" placeholder="Search in all columns..." style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div id="eperson-table"></div>
                </div>
            </div>
        </div>

        <!-- Tabulator CDN -->
        <link href="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const data = [
                    <c:forEach var="item" items="${listMerge}" varStatus="loop">
                        {
                            idEmployee: "${item.idEmployee}",
                            employeeNumber: "${item.employeeNumber}",
                            lastName: "${item.lastName}",
                            firstName: "${item.firstName}",
                            payRate: "${item.payRate}",
                            payRatesId: "${item.payRatesId}",
                            vacationDays: "${item.vacationDays}",
                            paidToDate: "${item.paidToDate}",
                            paidLastYear: "${item.paidLastYear}"
                        }<c:if test="${!loop.last}">,</c:if>
                    </c:forEach>
                ];

                var table = new Tabulator("#eperson-table", {
                    data: data,
                    layout: "fitDataStretch",
                    pagination: "local",
                    paginationSize: 10,
                    movableColumns: true,
                    columns: [
                        { title: "ID", field: "idEmployee", headerFilter: "input" },
                        { title: "Emp No", field: "employeeNumber", headerFilter: "input" },
                        { title: "Last Name", field: "lastName", headerFilter: "input" },
                        { title: "First Name", field: "firstName", headerFilter: "input" },
                        { title: "Pay Rate", field: "payRate", headerFilter: "input" },
                        { title: "Pay Rate ID", field: "payRatesId", headerFilter: "input" },
                        { title: "Vacation Days", field: "vacationDays", headerFilter: "input" },
                        { title: "Paid to Date", field: "paidToDate", headerFilter: "input" },
                        { title: "Paid Last Year", field: "paidLastYear", headerFilter: "input" }
                    ]
                });

                document.getElementById("search-input").addEventListener("input", function(e) {
                    table.setFilter(function(data) {
                        let match = false;
                        let term = e.target.value.toLowerCase();
                        for (let key in data) {
                            if (data[key] && data[key].toString().toLowerCase().includes(term)) {
                                match = true;
                                break;
                            }
                        }
                        return match;
                    });
                });
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
