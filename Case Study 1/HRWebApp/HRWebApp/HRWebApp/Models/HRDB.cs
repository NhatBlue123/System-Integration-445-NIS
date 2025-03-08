namespace HRWebApp.Models
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class HRDB : DbContext
    {
        public HRDB()
            : base("name=HRDB")
        {
        }

        public virtual DbSet<Benefit_Plans> Benefit_Plans { get; set; }
        public virtual DbSet<Emergency_Contacts> Emergency_Contacts { get; set; }
        public virtual DbSet<Employment> Employments { get; set; }
        public virtual DbSet<Job_History> Job_History { get; set; }
        public virtual DbSet<Personal> Personals { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Benefit_Plans>()
                .Property(e => e.Benefit_Plan_ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Benefit_Plans>()
                .Property(e => e.Deductable)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Benefit_Plans>()
                .HasMany(e => e.Personals)
                .WithOptional(e => e.Benefit_Plans1)
                .HasForeignKey(e => e.Benefit_Plans)
                .WillCascadeOnDelete();

            modelBuilder.Entity<Emergency_Contacts>()
                .Property(e => e.Employee_ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Employment>()
                .Property(e => e.Employee_ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.Employee_ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.Supervisor)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.Departmen_Code)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.Salary_Type)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Job_History>()
                .Property(e => e.Hours_per_Week)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Personal>()
                .Property(e => e.Employee_ID)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Personal>()
                .Property(e => e.Zip)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Personal>()
                .Property(e => e.Benefit_Plans)
                .HasPrecision(18, 0);

            modelBuilder.Entity<Personal>()
                .HasOptional(e => e.Emergency_Contacts)
                .WithRequired(e => e.Personal);

            modelBuilder.Entity<Personal>()
                .HasOptional(e => e.Employment)
                .WithRequired(e => e.Personal);

            modelBuilder.Entity<Personal>()
                .HasMany(e => e.Job_History)
                .WithRequired(e => e.Personal)
                .WillCascadeOnDelete(false);
        }
    }
}
