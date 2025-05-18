<%-- 
    Document   : addEmployee
    Created on : May 18, 2025, 4:22:48 PM
    Author     : bluez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>

<tiles:insertDefinition name="layouts">
    <tiles:putAttribute name="body">
        <style>
            .form-container {
                display: flex;
                justify-content: space-between;
                gap: 50px;
                padding: 20px;
            }

            .form-section {
                flex: 1;
            }

            .form-section h2 {
                margin-bottom: 10px;
            }

            label {
                display: block;
                margin-top: 10px;
            }

            input[type="text"],
            input[type="number"],
            input[type="email"],
            input[type="password"],
            input[type="date"],
            input[type="tel"] {
                width: 100%;
                padding: 6px;
                margin-top: 4px;
                box-sizing: border-box;
            }

            button[type="submit"] {
                padding: 10px 20px;
                font-size: 16px;
                margin-top: 20px;
                cursor: pointer;
            }

            .submit-container {
                text-align: center;
            }
        </style>

        <form:form method="POST" action="${contextPath}/admin/employee/createEmployee" modelAttribute="employee" id="employeeForm">
            <div class="form-container">
                <div class="form-section">
                    <h2>Employee Info</h2>
                    <label>Employee Number:</label>
                    <form:input path="employeeNumber" />

                    <label>Id Employee:</label>
                    <form:input path="idEmployee" />

                    <label>First Name:</label>
                    <form:input path="firstName" />

                    <label>Last Name:</label>
                    <form:input path="lastName" />

                    <label>SSN:</label>
                    <form:input path="ssn" />

                    <label>Pay Rate:</label>
                    <form:input path="payRate" />

                    <label>Pay Rates ID:</label>
                    <form:input path="payRatesId" />

                    <label>Vacation Days:</label>
                    <form:input path="vacationDays" />

                    <label>Paid To Date:</label>
                    <form:input path="paidToDate" />

                    <label>Paid Last Year:</label>
                    <form:input path="paidLastYear" />
                </div>
            </div>

            <div class="submit-container">
                <button type="submit">Add Employee</button>
            </div>
        </form:form>

      <script>
    document.getElementById("employeeForm").addEventListener("submit", function(event) {
        function isNumber(value) {
            return /^\d+$/.test(value);
        }

        const getVal = name => document.getElementsByName(name)[0].value.trim();

        const employeeNumber = getVal("employeeNumber");
        const idEmployee = getVal("idEmployee");
        const firstName = getVal("firstName");
        const lastName = getVal("lastName");
        const ssn = getVal("ssn");
        const payRate = getVal("payRate");
        const payRatesId = getVal("payRatesId");
        const vacationDays = getVal("vacationDays");
        const paidToDate = getVal("paidToDate");
        const paidLastYear = getVal("paidLastYear");

        let errors = [];

        // Bắt buộc nhập
        if (!employeeNumber || !idEmployee || !firstName || !lastName || !ssn) {
            errors.push("Vui lòng nhập đầy đủ các trường bắt buộc.");
        }

        // Kiểm tra độ dài số và chuỗi
        if (employeeNumber.length > 10 || !isNumber(employeeNumber)) {
            errors.push("Employee Number phải là số và tối đa 10 chữ số.");
        }

        if (idEmployee.length > 11 || !isNumber(idEmployee)) {
            errors.push("ID Employee phải là số và tối đa 11 chữ số.");
        }

        if (firstName.length > 45) {
            errors.push("First Name tối đa 45 ký tự.");
        }

        if (lastName.length > 45) {
            errors.push("Last Name tối đa 45 ký tự.");
        }

        if (ssn.length > 10 || !isNumber(ssn)) {
            errors.push("SSN phải là số và tối đa 10 chữ số.");
        }

        if (payRate.length > 40) {
            errors.push("Pay Rate tối đa 40 ký tự.");
        }

        if (payRatesId && (!isNumber(payRatesId) || payRatesId.length > 11)) {
            errors.push("Pay Rates ID phải là số và tối đa 11 chữ số.");
        }

        if (vacationDays && (!isNumber(vacationDays) || vacationDays.length > 11)) {
            errors.push("Vacation Days phải là số và tối đa 11 chữ số.");
        }

        if (paidToDate && (!isNumber(paidToDate) || paidToDate.length > 2)) {
            errors.push("Paid To Date phải là số và tối đa 2 chữ số.");
        }

        if (paidLastYear && (!isNumber(paidLastYear) || paidLastYear.length > 2)) {
            errors.push("Paid Last Year phải là số và tối đa 2 chữ số.");
        }

        if (errors.length > 0) {
            alert("Thông báo:\n" + errors.join("\n"));
            event.preventDefault();
            return;
        }

        alert("Thành công: Dữ liệu đã hợp lệ!");
    });
</script>


    </tiles:putAttribute>
</tiles:insertDefinition>
