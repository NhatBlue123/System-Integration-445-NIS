<%-- 
    Document   : editEmployee
    Created on : May 18, 2025, 11:54:09 PM
    Author     : bluez
--%>

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
        <h2>Edit Employee</h2>

        <form:form method="post" action="${contextPath}/admin/employee/updateEmployee" modelAttribute="employee" id="EditEmployee">
            <form:hidden path="idEmployee"/>

            <div>
                <label>Employee Number:</label>
                <form:input path="employeeNumber"/>
            </div>
            <div>
                <label>First Name:</label>
                <form:input path="firstName"/>
            </div>
            <div>
                <label>Last Name:</label>
                <form:input path="lastName"/>
            </div>
            <div>
                <label>SSN:</label>
                <form:input path="ssn"/>
            </div>
            <div>
                <label>Pay Rate:</label>
                <form:input path="payRate"/>
            </div>
            <div>
                <label>Pay Rate ID:</label>
                <form:input path="payRatesId"/>
            </div>
            <div>
                <label>Vacation Days:</label>
                <form:input path="vacationDays"/>
            </div>
            <div>
                <label>Paid To Date:</label>
                <form:input path="paidToDate"/>
            </div>
            <div>
                <label>Paid Last Year:</label>
                <form:input path="paidLastYear"/>
            </div>

            <div>
                <button type="submit">Save</button>
            </div>
        </form:form>
        <script>
            document.getElementById("EditEmployee").addEventListener("submit", function (event) {
                function isNumber(value) {
                    return /^\d+$/.test(value);
                }

                const getVal = name => document.getElementsByName(name)[0].value.trim();

                const employeeNumber = getVal("employeeNumber");
                const firstName = getVal("firstName");
                const lastName = getVal("lastName");
                const ssn = getVal("ssn");
                const payRate = getVal("payRate");
                const payRatesId = getVal("payRatesId");
                const vacationDays = getVal("vacationDays");
                const paidToDate = getVal("paidToDate");
                const paidLastYear = getVal("paidLastYear");

                let errors = [];

               

                // Kiểm tra độ dài số và chuỗi
                if (employeeNumber.length > 10 || !isNumber(employeeNumber)) {
                    errors.push("Employee Number phải là số và tối đa 10 chữ số.");
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


