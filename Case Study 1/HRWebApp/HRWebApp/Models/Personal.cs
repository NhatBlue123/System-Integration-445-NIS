namespace HRWebApp.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Personal")]
    public partial class Personal
    {
        public Personal()
        {
            Job_History = new HashSet<Job_History>();
        }

        [Key]
        [Column(TypeName = "numeric")]
        public decimal Employee_ID { get; set; }

        [StringLength(50)]
        public string First_Name { get; set; }

        [StringLength(50)]
        public string Last_Name { get; set; }

        [StringLength(50)]
        public string Middle_Initial { get; set; }

        [StringLength(50)]
        public string Address1 { get; set; }

        [StringLength(50)]
        public string Address2 { get; set; }

        [StringLength(50)]
        public string City { get; set; }

        [StringLength(50)]
        public string State { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Zip { get; set; }

        [StringLength(50)]
        public string Email { get; set; }

        [StringLength(50)]
        public string Phone_Number { get; set; }

        [StringLength(50)]
        public string Social_Security_Number { get; set; }

        [StringLength(50)]
        public string Drivers_License { get; set; }

        [StringLength(50)]
        public string Marital_Status { get; set; }

        public bool? Gender { get; set; }

        public bool Shareholder_Status { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Benefit_Plans { get; set; }

        [StringLength(50)]
        public string Ethnicity { get; set; }

        public virtual Benefit_Plans Benefit_Plans1 { get; set; }

        public virtual Emergency_Contacts Emergency_Contacts { get; set; }

        public virtual Employment Employment { get; set; }

        public virtual ICollection<Job_History> Job_History { get; set; }
    }
}
