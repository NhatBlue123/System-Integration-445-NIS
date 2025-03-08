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
    public class BenefitPlansController : Controller
    {
        private HRDB db = new HRDB();

        // GET: BenefitPlans
        public ActionResult Index()
        {
            return View(db.Benefit_Plans.ToList());
        }

        // GET: BenefitPlans/Details/5
        public ActionResult Details(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Benefit_Plans benefit_Plans = db.Benefit_Plans.Find(id);
            if (benefit_Plans == null)
            {
                return HttpNotFound();
            }
            return View(benefit_Plans);
        }

        // GET: BenefitPlans/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: BenefitPlans/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Benefit_Plan_ID,Plan_Name,Deductable,Percentage_CoPay")] Benefit_Plans benefit_Plans)
        {
            if (ModelState.IsValid)
            {
                db.Benefit_Plans.Add(benefit_Plans);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(benefit_Plans);
        }

        // GET: BenefitPlans/Edit/5
        public ActionResult Edit(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Benefit_Plans benefit_Plans = db.Benefit_Plans.Find(id);
            if (benefit_Plans == null)
            {
                return HttpNotFound();
            }
            return View(benefit_Plans);
        }

        // POST: BenefitPlans/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Benefit_Plan_ID,Plan_Name,Deductable,Percentage_CoPay")] Benefit_Plans benefit_Plans)
        {
            if (ModelState.IsValid)
            {
                db.Entry(benefit_Plans).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(benefit_Plans);
        }

        // GET: BenefitPlans/Delete/5
        public ActionResult Delete(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Benefit_Plans benefit_Plans = db.Benefit_Plans.Find(id);
            if (benefit_Plans == null)
            {
                return HttpNotFound();
            }
            return View(benefit_Plans);
        }

        // POST: BenefitPlans/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(decimal id)
        {
            Benefit_Plans benefit_Plans = db.Benefit_Plans.Find(id);
            db.Benefit_Plans.Remove(benefit_Plans);
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
