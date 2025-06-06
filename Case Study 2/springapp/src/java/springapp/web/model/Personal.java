/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.model;

/**
 *
 * @author bluez
 */
public class Personal {

                //Personal pe = new Personal(eperson.getEmployee_ID(), eperson.getFirstName(), eperson.getLast_Name(), eperson.getBenefit_Plans(), eperson.getMiddle_Initial(), eperson.getAddress1(), eperson.getAddress2(), eperson.getCity(), eperson.getState(), eperson.getZip(), eperson.getPhone_Number(), eperson.getSocial_Security_Number(), eperson.getDrivers_License(), eperson.getMarital_Status(), PERSONAL_API_URL, eperson.isGender(), eperson.isShareholder_Status(), eperson.getEthnicity());

    public Personal(int Employee_ID, String First_Name, String Last_Name, int Benefit_Plans, String Middle_Initial, String Address1, String Address2, String City, String State, int Zip, String Email, String Phone_Number, String Social_Security_Number, String Drivers_License, String Marital_Status, boolean Gender, boolean Shareholder_Status, String Ethnicity) {
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
    public Personal()
    {
        
    }

    public int Employee_ID;

    public String First_Name;

    public String Last_Name;
    
    public String Full_Name = First_Name + " " + Last_Name;

    public int getEmployee_ID() {
        return Employee_ID;
    }

    public void setEmployee_ID(int Employee_ID) {
        this.Employee_ID = Employee_ID;
    }

    public String getFull_Name()
    {
        return this.Full_Name;
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
