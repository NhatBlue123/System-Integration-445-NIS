namespace HRWebApp.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Emergency_Contacts
    {
        [Key]
        [Column(TypeName = "numeric")]
        public decimal Employee_ID { get; set; }

        [StringLength(50)]
        public string Emergency_Contact_Name { get; set; }

        [StringLength(50)]
        public string Phone_Number { get; set; }

        [StringLength(50)]
        public string Relationship { get; set; }

        public virtual Personal Personal { get; set; }
    }
}
