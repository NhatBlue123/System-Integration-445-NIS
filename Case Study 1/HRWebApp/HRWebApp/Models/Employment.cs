namespace HRWebApp.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Employment")]
    public partial class Employment
    {
        [Key]
        [Column(TypeName = "numeric")]
        public decimal Employee_ID { get; set; }

        [StringLength(50)]
        public string Employment_Status { get; set; }

        public DateTime? Hire_Date { get; set; }

        [StringLength(50)]
        public string Workers_Comp_Code { get; set; }

        public DateTime? Termination_Date { get; set; }

        public DateTime? Rehire_Date { get; set; }

        public DateTime? Last_Review_Date { get; set; }

        public virtual Personal Personal { get; set; }
    }
}
