using System;

namespace HRWebApp.Models
{
    public class Employee
    {
        public int employeeNumber { get; set; }
        public decimal idEmployee { get; set; }
        public String lastName { get; set; }
        public String firstName { get; set; }
        //     public String fullName = firstName + " " + lastName;
        public long ssn { get; set; }
        public String payRate { get; set; }

        public int payRatesId { get; set; }
        public int vacationDays { get; set; }
        public Byte paidToDate { get; set; }
        public Byte paidLastYear { get; set; }

    }


}