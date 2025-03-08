namespace HRWebApp.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Job_History
    {
        [Column(TypeName = "numeric")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public decimal ID { get; set; }

        [Column(TypeName = "numeric")]
        public decimal Employee_ID { get; set; }

        [StringLength(50)]
        public string Department { get; set; }

        [StringLength(50)]
        public string Division { get; set; }

        public DateTime? Start_Date { get; set; }

        public DateTime? End_Date { get; set; }

        [StringLength(50)]
        public string Job_Title { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Supervisor { get; set; }

        [StringLength(50)]
        public string Job_Category { get; set; }

        [StringLength(50)]
        public string Location { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Departmen_Code { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Salary_Type { get; set; }

        [StringLength(50)]
        public string Pay_Period { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Hours_per_Week { get; set; }

        public bool? Hazardous_Training { get; set; }

        public virtual Personal Personal { get; set; }
    }
}
