/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.model;

/**
 *
 * @author Bluez
 */
public class MergedEmployeePersonal {

    //spring 
    private int employeeNumber;//
    private int idEmployee; //
    private String lastName;//
    private String firstName;//
    private long ssn;//
    private String payRate;
    private Integer payRatesId;
    private Integer vacationDays;
    private Byte paidToDate;
    private Byte paidLastYear;
    // hr
    private String Middle_Initial;//
    private String Address1;//
    private String Address2;//
    private String City;//
    private String State;//
    private int Zip;//

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getSsn() {
        return ssn;
    }

    public void setSsn(long ssn) {
        this.ssn = ssn;
    }

    public String getPayRate() {
        return payRate;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }

    public Integer getPayRatesId() {
        return payRatesId;
    }

    public void setPayRatesId(Integer payRatesId) {
        this.payRatesId = payRatesId;
    }

    public Integer getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Integer vacationDays) {
        this.vacationDays = vacationDays;
    }

    public Byte getPaidToDate() {
        return paidToDate;
    }

    public void setPaidToDate(Byte paidToDate) {
        this.paidToDate = paidToDate;
    }

    public Byte getPaidLastYear() {
        return paidLastYear;
    }

    public void setPaidLastYear(Byte paidLastYear) {
        this.paidLastYear = paidLastYear;
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

    public String getBenefit_Plan() {
        return Benefit_Plan;
    }

    public void setBenefit_Plan(String Benefit_Plan) {
        this.Benefit_Plan = Benefit_Plan;
    }
    private String Email;//
    private String Phone_Number;//
    private String Social_Security_Number;
    private String Drivers_License;//
    private String Marital_Status;//
    private boolean Gender;//
    private boolean Shareholder_Status;//
    
    private String Benefit_Plan = null;

}
