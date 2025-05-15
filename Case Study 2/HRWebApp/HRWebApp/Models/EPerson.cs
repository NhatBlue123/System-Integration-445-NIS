namespace HRWebApp.Models
{
    public class EPerson
    {
        public int Employee_ID { get; set; }
        public string First_Name { get; set; }
        public string Last_Name { get; set; }
        public string Middle_Initial { get; set; }
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public decimal? Zip { get; set; }
        public string Email { get; set; }
        public string Phone_Number { get; set; }
        public string Social_Security_Number { get; set; }
        public bool Gender { get; set; }
        public bool Shareholder_Status { get; set; }

        public int Benefit_Plans { get; set; }

        public string Ethnicity { get; set; }
        public string Marital_Status { get; set; }
        public string Drivers_License { get; set; }

    }
}