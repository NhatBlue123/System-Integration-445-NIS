<%-- 
    Document   : addEPerson
    Created on : May 8, 2025, 10:27:29 PM
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

        <form:form method="POST" action="${contextPath}/admin/EPerson/createEPerson" modelAttribute="eperson" id="EPersonForm">
            <div class="form-container">
                <!-- Employee Section -->
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

                <!-- Personal Section -->
                <div class="form-section">
                    <h2>Personal Info</h2>
                    <label>Employee ID:</label>
                    <form:input path="Employee_ID" />
                    <label>First Name:</label>
                    <form:input path="First_Name" />

                    <label>Last Name:</label>
                    <form:input path="Last_Name" />

                    <label>Middle Initial:</label>
                    <form:input path="Middle_Initial" />

                    <label>Address 1:</label>
                    <form:input path="Address1" />

                    <label>Address 2:</label>
                    <form:input path="Address2" />

                    <label>City:</label>
                    <form:input path="City" />

                    <label>State:</label>
                    <form:input path="State" />

                    <label>Zip:</label>
                    <form:input path="Zip" />

                    <label>Email:</label>
                    <form:input path="Email" />

                    <label>Phone Number:</label>
                    <form:input path="Phone_Number" />

                    <label>SSN (Personal):</label>
                    <form:input path="Social_Security_Number" />

                    <label>Driver's License:</label>
                    <form:input path="Drivers_License" />

                    <label>Marital Status:</label>
                    <form:input path="Marital_Status" />

                    <label>Gender:</label>
                    <form:checkbox path="Gender" />

                    <label>Shareholder Status:</label>
                    <form:checkbox path="Shareholder_Status" />

                    <label>Benefit Plans:</label>
                    <form:input path="Benefit_Plans" />

                    <label>Ethnicity:</label>
                    <form:input path="Ethnicity" />
                </div>
            </div>

            <div class="submit-container">
                <button type="submit">Save EPerson</button>
            </div>
        </form:form>
        <script>
            document.getElementById("EPersonForm").addEventListener("submit", function (event) {
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

                // Personal
                const personalEmployeeID = getVal("Employee_ID");
                const personalFirstName = getVal("First_Name");
                const personalLastName = getVal("Last_Name");
                const middleInitial = getVal("Middle_Initial");
                const address1 = getVal("Address1");
                const address2 = getVal("Address2");
                const city = getVal("City");
                const state = getVal("State");
                const zip = getVal("Zip");
                const email = getVal("Email");
                const phone = getVal("Phone_Number");
                const ssnPersonal = getVal("Social_Security_Number");
                const driverLicense = getVal("Drivers_License");
                const maritalStatus = getVal("Marital_Status");
                const benefitPlans = getVal("Benefit_Plans");
                const ethnicity = getVal("Ethnicity");

                let errors = [];

                 === EMPLOYEE VALIDATION ===

                if (!employeeNumber || !idEmployee || !firstName || !lastName || !ssn) {
                    errors.push("Vui lòng nhập đầy đủ các trường bắt buộc (Employee).");
                }

                if (employeeNumber.length > 10 || !isNumber(employeeNumber)) {
                    errors.push("Employee Number phải là số và tối đa 10 chữ số.");
                }

                if (idEmployee.length > 11 || !isNumber(idEmployee)) {
                    errors.push("ID Employee phải là số và tối đa 11 chữ số.");
                }

                if (firstName.length > 45) {
                    errors.push("First Name (Employee) tối đa 45 ký tự.");
                }

                if (lastName.length > 45) {
                    errors.push("Last Name (Employee) tối đa 45 ký tự.");
                }

                if (ssn.length > 10 || !isNumber(ssn)) {
                    errors.push("SSN (Employee) phải là số và tối đa 10 chữ số.");
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

                // === PERSONAL VALIDATION ===
                if (!personalEmployeeID || personalEmployeeID == 0) {
                    errors.push("Vui lòng nhập đầy đủ các trường bắt buộc (Personal).");
                }
                if (personalEmployeeID.length > 18 || !isNumber(personalEmployeeID)) {
                    errors.push("Employee ID(Personal)  phải là số và tối đa 18 chữ số.");
                }
                if (personalFirstName.length > 50) {
                    errors.push("First Name (Personal) tối đa 50 ký tự.");
                }

                if (personalLastName.length > 50) {
                    errors.push("Last Name (Personal) tối đa 50 ký tự.");
                }

                if (middleInitial.length > 50) {
                    errors.push("Middle Initial tối đa 50 ký tự.");
                }

                if (address1.length > 50 || address2.length > 50) {
                    errors.push("Address 1 và Address 2 tối đa 50 ký tự.");
                }

                if (city.length > 50) {
                    errors.push("City tối đa 50 ký tự.");
                }

                if (state.length > 50) {
                    errors.push("State tối đa 50 ký tự.");
                }

                if (zip && (!isNumber(zip) || zip.length > 18)) {
                    errors.push("Zip phải là số và tối đa 18 chữ số.");
                }

                if (email.length > 50) {
                    errors.push("Email tối đa 50 ký tự.");
                }

                if (phone.length > 50) {
                    errors.push("Phone Number tối đa 50 ký tự.");
                }

                if (ssnPersonal.length > 50) {
                    errors.push("SSN (Personal) tối đa 50 ký tự.");
                }

                if (driverLicense.length > 50) {
                    errors.push("Driver's License tối đa 50 ký tự.");
                }

                if (maritalStatus.length > 50) {
                    errors.push("Marital Status tối đa 50 ký tự.");
                }

                if (benefitPlans && (!isNumber(benefitPlans) || benefitPlans.length > 18)) {
                    errors.push("Benefit Plans phải là số và tối đa 18 chữ số.");
                }

                if (ethnicity.length > 50) {
                    errors.push("Ethnicity tối đa 50 ký tự.");
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
