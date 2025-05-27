using System;

namespace HRWebApp.Models
{
    public class EPerson
    {
        //tạo thêm cái này cho tôi

        private int employeeNumber { get; set; }
        private int idEmployee { get; set; }
        private String lastName { get; set; }
        private String firstName { get; set; }
           private String fullName = "";
        private long ssn { get; set; }
        private String payRate { get; set; }
        private int payRatesId { get; set; }
        private int vacationDays { get; set; }
        private Byte paidToDate { get; set; }
        private Byte paidLastYear { get; set; }




        public decimal Employee_ID { get; set; } // not null
        public string First_Name { get; set; }
        public string Last_Name { get; set; }

        public string Marital_Status { get; set; }
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public string City { get; set; }

        public string State { get; set; }
        public decimal? Zip { get; set; }
        public string Email { get; set; }
        public string Phone_Number { get; set; }
        public string Social_Security_Number { get; set; }
        public string Drivers_License { get; set; }
     
        public bool? Gender { get; set; }
        public bool Shareholder_Status { get; set; }
        public decimal? Benefit_Plans { get; set; }
        public string Ethnicity { get; set; }
        // tạo tôi mấy thuộc tính này

    }

}