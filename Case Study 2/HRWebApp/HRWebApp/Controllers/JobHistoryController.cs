using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using HRWebApp.Models;

namespace HRWebApp.Controllers
{
    public class JobHistoryController : Controller
    {
        private HRDB db = new HRDB();

        // GET: JobHistory
        public ActionResult Index()
        {
            var job_History = db.Job_History.Include(j => j.Personal);
            return View(job_History.ToList());
        }

        // GET: JobHistory/Details/5
        public ActionResult Details(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Job_History job_History = db.Job_History.Find(id);
            if (job_History == null)
            {
                return HttpNotFound();
            }
            return View(job_History);
        }

        // GET: JobHistory/Create
        public ActionResult Create()
        {
            ViewBag.Employee_ID = new SelectList(db.Personals, "Employee_ID", "First_Name");
            return View();
        }

        // POST: JobHistory/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ID,Employee_ID,Department,Division,Start_Date,End_Date,Job_Title,Supervisor,Job_Category,Location,Departmen_Code,Salary_Type,Pay_Period,Hours_per_Week,Hazardous_Training")] Job_History job_History)
        {
            if (ModelState.IsValid)
            {
                db.Job_History.Add(job_History);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.Employee_ID = new SelectList(db.Personals, "Employee_ID", "First_Name", job_History.Employee_ID);
            return View(job_History);
        }

        // GET: JobHistory/Edit/5
        public ActionResult Edit(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Job_History job_History = db.Job_History.Find(id);
            if (job_History == null)
            {
                return HttpNotFound();
            }
            ViewBag.Employee_ID = new SelectList(db.Personals, "Employee_ID", "First_Name", job_History.Employee_ID);
            return View(job_History);
        }

        // POST: JobHistory/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ID,Employee_ID,Department,Division,Start_Date,End_Date,Job_Title,Supervisor,Job_Category,Location,Departmen_Code,Salary_Type,Pay_Period,Hours_per_Week,Hazardous_Training")] Job_History job_History)
        {
            if (ModelState.IsValid)
            {
                db.Entry(job_History).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.Employee_ID = new SelectList(db.Personals, "Employee_ID", "First_Name", job_History.Employee_ID);
            return View(job_History);
        }

        // GET: JobHistory/Delete/5
        public ActionResult Delete(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Job_History job_History = db.Job_History.Find(id);
            if (job_History == null)
            {
                return HttpNotFound();
            }
            return View(job_History);
        }

        // POST: JobHistory/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(decimal id)
        {
            Job_History job_History = db.Job_History.Find(id);
            db.Job_History.Remove(job_History);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
