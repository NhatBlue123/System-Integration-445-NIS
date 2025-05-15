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
     public EPerson()
    {
        
    }

    public EPerson(int employeeNumber, int idEmployee, String lastName, String firstName, long ssn, String payRate, Integer payRatesId, Integer vacationDays, Byte paidToDate, Byte paidLastYear, int Employee_ID, String First_Name, String Last_Name, int Benefit_Plans, String Middle_Initial, String Address1, String Address2, String City, String State, int Zip, String Email, String Phone_Number, String Social_Security_Number, String Drivers_License, String Marital_Status, boolean Gender, boolean Shareholder_Status, String Ethnicity) {
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
    
    

   private int employeeNumber;
     private int idEmployee;
     private String lastName;
     private String firstName;
     private String fullName = this.lastName + " " + this.fullName;
     private long ssn;
     private String payRate;
     private Integer payRatesId;
     private Integer vacationDays;
     private Byte paidToDate;
     private Byte paidLastYear;

    
   
    public int getEmployeeNumber() {
        return this.employeeNumber;
    }
    
    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    public int getIdEmployee() {
        return this.idEmployee;
    }
    public String getFullName()
    {
        return this.fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public long getSsn() {
        return this.ssn;
    }
    
    public void setSsn(long ssn) {
        this.ssn = ssn;
    }
    public String getPayRate() {
        return this.payRate;
    }
    
    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }
    public Integer getPayRatesId() {
        return this.payRatesId;
    }
    
    public void setPayRatesId(Integer payRatesId) {
        this.payRatesId = payRatesId;
    }
    public Integer getVacationDays() {
        return this.vacationDays;
    }
    
    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }
    public Byte getPaidToDate() {
        return this.paidToDate;
    }
    
    public void setPaidToDate(Byte paidToDate) {
        this.paidToDate = paidToDate;
    }
    public Byte getPaidLastYear() {
        return this.paidLastYear;
    }
    
    public void setPaidLastYear(Byte paidLastYear) {
        this.paidLastYear = paidLastYear;
    }
  
     public int Employee_ID;

    public String First_Name;

    public String Last_Name;

    public int getEmployee_ID() {
        return Employee_ID;
    }

    public void setEmployee_ID(int Employee_ID) {
        this.Employee_ID = Employee_ID;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String Last_Name) {
        this.Last_Name = Last_Name;
    }

    public String getMiddle_Initial() {
        return Middle_Initial;
    }

    public void setMiddle_Initial(String Middle_Initial) {
        this.Middle_Initial = Middle_Initial;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public int getZip() {
        return Zip;
    }

    public void setZip(int Zip) {
        this.Zip = Zip;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String Phone_Number) {
        this.Phone_Number = Phone_Number;
    }

    public String getSocial_Security_Number() {
        return Social_Security_Number;
    }

    public void setSocial_Security_Number(String Social_Security_Number) {
        this.Social_Security_Number = Social_Security_Number;
    }

    public String getDrivers_License() {
        return Drivers_License;
    }

    public void setDrivers_License(String Drivers_License) {
        this.Drivers_License = Drivers_License;
    }

    public String getMarital_Status() {
        return Marital_Status;
    }

    public void setMarital_Status(String Marital_Status) {
        this.Marital_Status = Marital_Status;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public boolean isShareholder_Status() {
        return Shareholder_Status;
    }

    public void setShareholder_Status(boolean Shareholder_Status) {
        this.Shareholder_Status = Shareholder_Status;
    }
      
    public int Benefit_Plans;

    public int getBenefit_Plans() {
        return Benefit_Plans;
    }

    public void setBenefit_Plans(int Benefit_Plans) {
        this.Benefit_Plans = Benefit_Plans;
    }

    public String Middle_Initial;

    public String Address1;

    public String Address2;

    public String City;

    public String State;

    public int Zip;

    public String Email;

    public String Phone_Number;

    public String Social_Security_Number;

    public String Drivers_License;

    public String Marital_Status;

    public String getEthnicity() {
        return Ethnicity;
    }

    public void setEthnicity(String Ethnicity) {
        this.Ethnicity = Ethnicity;
    }

    public boolean Gender;

    public boolean Shareholder_Status;
    
    public String Ethnicity;

}