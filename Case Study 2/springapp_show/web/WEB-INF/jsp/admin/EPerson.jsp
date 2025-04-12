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
                    <!-- Add search box -->
                    <div class="search-container" style="margin-bottom: 20px;">
                        <input type="text" id="search-input" placeholder="Search in all columns..." style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                    </div>
                    <div id="eperson-table"></div>
                </div>
            </div>
        </div>

        <link href="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/css/tabulator.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/tabulator-tables@5.4.4/dist/js/tabulator.min.js"></script>

        <style>
            #eperson-table {
                max-width: 100%;
                overflow-x: auto;
            }
            .search-container {
                margin-bottom: 20px;
            }
            .tabulator .tabulator-header .tabulator-col {
                background-color: #f8f9fa;
            }
        </style>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                fetch("http://localhost:8888/springapp/admin/employee/getLimitEmployee/1000")
                    .then(response => response.json())
                    .then(data => {
                        var table = new Tabulator("#eperson-table", {
                            data: data,
                            layout: "fitDataStretch",
                            pagination: "local",
                            paginationSize: 10,
                            movableColumns: true,
                            headerFilterPlaceholder: "Filter...",
                            columns: [
                                { title: "Employee ID", field: "idEmployee", headerFilter: "input" },
                                { title: "Employee Number", field: "employeeNumber", headerFilter: "input" },
                                { title: "Last Name", field: "lastName", headerFilter: "input" },
                                { title: "First Name", field: "firstName", headerFilter: "input" },
                                { title: "Middle Initial", field: "middleInitial", headerFilter: "input" },
                                { title: "Address 1", field: "address1", headerFilter: "input" },
                                { title: "Address 2", field: "address2", headerFilter: "input" },
                                { title: "City", field: "city", headerFilter: "input" },
                                { title: "State", field: "state", headerFilter: "input" },
                                { title: "Zip Code", field: "zip", headerFilter: "input" },
                                { title: "Email", field: "email", headerFilter: "input" },
                                { title: "Phone", field: "phone", headerFilter: "input" },
                                { title: "SSN", field: "ssn", headerFilter: "input" },
                                { title: "Driver's License", field: "driverLicense", headerFilter: "input" },
                                { title: "Marital Status", field: "maritalStatus", headerFilter: "input" },
                                { title: "Gender", field: "gender", headerFilter: "input" },
                                { title: "Shareholder Status", field: "shareholderStatus", headerFilter: "input" },
                                { title: "Benefit Plans", field: "benefitPlans", headerFilter: "input" },
                                { title: "Ethnicity", field: "ethnicity", headerFilter: "input" },
                                { title: "Pay Rate", field: "payRate", headerFilter: "input" },
                                { title: "Pay Rate ID", field: "payRatesId", headerFilter: "input" },
                                { title: "Vacation Days", field: "vacationDays", headerFilter: "input" },
                                { title: "Paid to Date", field: "paidToDate", headerFilter: "input" },
                                { title: "Paid Last Year", field: "paidLastYear", headerFilter: "input" },
                            ]
                        });

                        document.getElementById("search-input").addEventListener("input", function(e) {
                            table.setFilter(function(data) {
                                let match = false;
                                let searchTerm = e.target.value.toLowerCase();
                                
                                for (let field in data) {
                                    if (data[field] && data[field].toString().toLowerCase().includes(searchTerm)) {
                                        match = true;
                                        break;
                                    }
                                }
                                return match;
                            });
                        });
                    })
                    .catch(error => {
                        console.error("ERROR COLLECT DÂT:", error);
                    });
            });
        </script>
    </tiles:putAttribute>
</tiles:insertDefinition>
