namespace HRWebApp.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Benefit_Plans
    {
        public Benefit_Plans()
        {
            Personals = new HashSet<Personal>();
        }

        [Key]
        [Column(TypeName = "numeric")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public decimal Benefit_Plan_ID { get; set; }

        [StringLength(50)]
        public string Plan_Name { get; set; }

        [Column(TypeName = "numeric")]
        public decimal? Deductable { get; set; }

        public int? Percentage_CoPay { get; set; }

        public virtual ICollection<Personal> Personals { get; set; }
    }
}
