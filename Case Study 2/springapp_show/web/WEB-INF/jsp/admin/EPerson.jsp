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
                    ssn: "${item.ssn}",
                    payRate: "${item.payRate}",
                    payRatesId: "${item.payRatesId}",
                    vacationDays: "${item.vacationDays}",
                    paidToDate: "${item.paidToDate}",
                    paidLastYear: "${item.paidLastYear}",
                    Middle_Initial: "${item.middle_Initial}",
                    Address1: "${item.address1}",
                    Address2: "${item.address2}",
                    City: "${item.city}",
                    State: "${item.state}",
                    Zip: "${item.zip}",
                    Email: "${item.email}",
                    Phone_Number: "${item.phone_Number}",
                    Social_Security_Number: "${item.social_Security_Number}",
                    Drivers_License: "${item.drivers_License}",
                    Marital_Status: "${item.marital_Status}",
                    Gender: "${item.gender}",
                    Shareholder_Status: "${item.shareholder_Status}",
                    Benefit_Plans: "${item.benefit_Plans}",
                    Ethnicity: "${item.ethnicity}"
            }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
            ];
//                    + "\"Middle_Initial\": \"%s\", "
//                    + "\"Address1\": \"%s\", "
//                    + "\"Address2\": \"%s\", "
//                    + "\"City\": \"%s\", "
//                    + "\"State\": \"%s\", "
//                    + "\"Zip\": \"%d\", "
//                    + "\"Email\": \"%s\","
//                    + "\"Phone_Number\": \"%s\", "
//                    + "\"Social_Security_Number\": \"%s\", "
//                    + "\"Drivers_License\": \"%s\", "
//                    + "\"Marital_Status\": \"%s\", "
//                    + "\"Gender\": \"%b\", "
//                    + "\"Shareholder_Status\": \"%b\", "
//                    + "\"Benefit_Plans\": \"%d\", "
//                    + "\"Ethnicity\": \"%s\", "
                    var table = new Tabulator("#eperson-table", {
                    data: data,
                            layout: "fitDataStretch",
                            pagination: "local",
                            paginationSize: 10,
                            movableColumns: true,
                            columns: [
                            {title: "ID", field: "idEmployee", headerFilter: "input"},
                            {title: "Emp No", field: "employeeNumber", headerFilter: "input"},
                            {title: "Last Name", field: "lastName", headerFilter: "input"},
                            {title: "First Name", field: "firstName", headerFilter: "input"},
                            {title: "SSN", field: "ssn", headerFilter: "input"},
                            {title: "Pay Rate", field: "payRate", headerFilter: "input"},
                            {title: "Pay Rate ID", field: "payRatesId", headerFilter: "input"},
                            {title: "Vacation Days", field: "vacationDays", headerFilter: "input"},
                            {title: "Paid to Date", field: "paidToDate", headerFilter: "input"},
                            {title: "Paid Last Year", field: "paidLastYear", headerFilter: "input"},
                            {title: "Middle Initial", field: "Middle_Initial", headerFilter: "input"},
                            {title: "Address 1", field: "Address1", headerFilter: "input"},
                            {title: "Address 2", field: "Address2", headerFilter: "input"},
                            {title: "City", field: "City", headerFilter: "input"},
                            {title: "State", field: "State", headerFilter: "input"},
                            {title: "Zip", field: "Zip", headerFilter: "input"},
                            {title: "Email", field: "Email", headerFilter: "input"},
                            {title: "Phone Number", field: "Phone_Number", headerFilter: "input"},
                            {title: "Social Security Number", field: "Social_Security_Number", headerFilter: "input"},
                            {title: "Drivers License", field: "Drivers_License", headerFilter: "input"},
                            {title: "Marital Status", field: "Marital_Status", headerFilter: "input"},
                            {title: "Gender", field: "Gender", headerFilter: "input"},
                            {title: "Shareholder Status", field: "Shareholder_Status", headerFilter: "input"},
                            {title: "Benefit Plans", field: "Benefit_Plans", headerFilter: "input"},
                            {title: "Ethnicity", field: "Ethnicity", headerFilter: "input"},
                            ]
                    });
                    document.getElementById("search-input").addEventListener("input", function (e) {
            table.setFilter(function (data) {
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