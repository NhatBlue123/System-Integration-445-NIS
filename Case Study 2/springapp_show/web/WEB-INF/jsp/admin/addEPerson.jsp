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

        <form:form method="POST" action="${contextPath}/EPerson/createEPerson" modelAttribute="eperson">
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

    </tiles:putAttribute>
</tiles:insertDefinition>
