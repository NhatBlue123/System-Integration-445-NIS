/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.model;


/**
 *
 * @author bluez
 */
public class EPerson {

    // ==================== EMPLOYEE FIELDS ====================
    private int employeeNumber;
    private int idEmployee;
    private String lastName;
    private String firstName;
    private long ssn;
    private String payRate;
    private Integer payRatesId;
    private Integer vacationDays;
    private Byte paidToDate;
    private Byte paidLastYear;
    private String fullName = this.lastName + " " + this.firstName;

    // ==================== PERSONAL FIELDS ====================
    private int Employee_ID;
    private String First_Name;
    private String Last_Name;
    private String Middle_Initial;
    private String Address1;
    private String Address2;
    private String City;
    private String State;
    private int Zip;
    private String Email;
    private String Phone_Number;
    private String Social_Security_Number;
    private String Drivers_License;
    private String Marital_Status;
    private boolean Gender;
    private boolean Shareholder_Status;
    private int Benefit_Plans;
    private String Ethnicity;

    // ==================== CONSTRUCTORS ====================
    public EPerson() {}

    public EPerson(
        int employeeNumber, int idEmployee, String lastName, String firstName, long ssn,
        String payRate, Integer payRatesId, Integer vacationDays, Byte paidToDate, Byte paidLastYear,
        int Employee_ID, String First_Name, String Last_Name, int Benefit_Plans, String Middle_Initial,
        String Address1, String Address2, String City, String State, int Zip, String Email,
        String Phone_Number, String Social_Security_Number, String Drivers_License,
        String Marital_Status, boolean Gender, boolean Shareholder_Status, String Ethnicity
    ) {
        this.employeeNumber = employeeNumber;
        this.idEmployee = idEmployee;
        this.lastName = lastName;
        this.firstName = firstName;
        this.ssn = ssn;
        this.payRate = payRate;
        this.payRatesId = payRatesId;
        this.vacationDays = vacationDays;
        this.paidToDate = paidToDate;
        this.paidLastYear = paidLastYear;

        this.Employee_ID = Employee_ID;
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Benefit_Plans = Benefit_Plans;
        this.Middle_Initial = Middle_Initial;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
        this.Email = Email;
        this.Phone_Number = Phone_Number;
        this.Social_Security_Number = Social_Security_Number;
        this.Drivers_License = Drivers_License;
        this.Marital_Status = Marital_Status;
        this.Gender = Gender;
        this.Shareholder_Status = Shareholder_Status;
        this.Ethnicity = Ethnicity;
    }

    // ==================== EMPLOYEE GETTERS/SETTERS ====================
    public int getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(int employeeNumber) { this.employeeNumber = employeeNumber; }

    public int getIdEmployee() { return idEmployee; }
    public void setIdEmployee(int idEmployee) { this.idEmployee = idEmployee; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public long getSsn() { return ssn; }
    public void setSsn(long ssn) { this.ssn = ssn; }

    public String getPayRate() { return payRate; }
    public void setPayRate(String payRate) { this.payRate = payRate; }

    public Integer getPayRatesId() { return payRatesId; }
    public void setPayRatesId(Integer payRatesId) { this.payRatesId = payRatesId; }

    public Integer getVacationDays() { return vacationDays; }
    public void setVacationDays(Integer vacationDays) { this.vacationDays = vacationDays; }

    public Byte getPaidToDate() { return paidToDate; }
    public void setPaidToDate(Byte paidToDate) { this.paidToDate = paidToDate; }

    public Byte getPaidLastYear() { return paidLastYear; }
    public void setPaidLastYear(Byte paidLastYear) { this.paidLastYear = paidLastYear; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    // ==================== PERSONAL GETTERS/SETTERS ====================
    public int getEmployee_ID() { return Employee_ID; }
    public void setEmployee_ID(int employee_ID) { Employee_ID = employee_ID; }

    public String getFirst_Name() { return First_Name; }
    public void setFirst_Name(String first_Name) { First_Name = first_Name; }

    public String getLast_Name() { return Last_Name; }
    public void setLast_Name(String last_Name) { Last_Name = last_Name; }

    public String getMiddle_Initial() { return Middle_Initial; }
    public void setMiddle_Initial(String middle_Initial) { Middle_Initial = middle_Initial; }

    public String getAddress1() { return Address1; }
    public void setAddress1(String address1) { Address1 = address1; }

    public String getAddress2() { return Address2; }
    public void setAddress2(String address2) { Address2 = address2; }

    public String getCity() { return City; }
    public void setCity(String city) { City = city; }

    public String getState() { return State; }
    public void setState(String state) { State = state; }

    public int getZip() { return Zip; }
    public void setZip(int zip) { Zip = zip; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getPhone_Number() { return Phone_Number; }
    public void setPhone_Number(String phone_Number) { Phone_Number = phone_Number; }

    public String getSocial_Security_Number() { return Social_Security_Number; }
    public void setSocial_Security_Number(String ssn) { Social_Security_Number = ssn; }

    public String getDrivers_License() { return Drivers_License; }
    public void setDrivers_License(String drivers_License) { Drivers_License = drivers_License; }

    public String getMarital_Status() { return Marital_Status; }
    public void setMarital_Status(String marital_Status) { Marital_Status = marital_Status; }

    public boolean isGender() { return Gender; }
    public void setGender(boolean gender) { Gender = gender; }

    public boolean isShareholder_Status() { return Shareholder_Status; }
    public void setShareholder_Status(boolean shareholder_Status) { Shareholder_Status = shareholder_Status; }

    public int getBenefit_Plans() { return Benefit_Plans; }
    public void setBenefit_Plans(int benefit_Plans) { Benefit_Plans = benefit_Plans; }

    public String getEthnicity() { return Ethnicity; }
    public void setEthnicity(String ethnicity) { Ethnicity = ethnicity; }
}
