using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Bogus;
using HRWebApp.Models;

namespace HRWebApp.Controllers
{
    public class PersonalsController : Controller
    {
        private HRDB db = new HRDB();

        // GET: Personals
        public ActionResult Index()
        {
            var personals = db.Personals.Include(p => p.Benefit_Plans1).Include(p => p.Emergency_Contacts).Include(p => p.Employment);
            return View(personals.ToList());
        }

        //GET: Personals/getAllPersonal
        public JsonResult getAllPersonal()
        {
           
            var personals = db.Personals
           .Select(p => new {
                  p.Employee_ID,
                  p.First_Name,
                  p.Last_Name,
                  p.Middle_Initial,
                  p.Address1,
                  p.Address2,
                  p.City,
                  p.State,
                  p.Zip,
                  p.Email,
                  p.Phone_Number,
                  p.Social_Security_Number,
                  p.Drivers_License,
                  p.Marital_Status,
                  p.Gender,
                  p.Shareholder_Status,
                  p.Benefit_Plans,
                  p.Ethnicity
            })
            .ToList();
            return Json(personals, JsonRequestBehavior.AllowGet);
        }


        // GET: Personals/getLimitPersonal?limit=111
        public JsonResult getLimitPersonal(int limit)
        {
            var fake = new Bogus.Faker();
            var personals = db.Personals
           .Select(p => new {
               p.Employee_ID,
               p.First_Name,
               p.Last_Name,
               p.Middle_Initial,
               p.Address1,
               p.Address2,
               p.City,
               p.State,
               p.Zip,
               p.Email,
               p.Phone_Number,
               p.Social_Security_Number,
               p.Drivers_License,
               p.Marital_Status,
               p.Gender,
               p.Shareholder_Status,
               p.Benefit_Plans,
               p.Ethnicity
           })
                .Take(limit)
                .ToList();

            return Json(personals, JsonRequestBehavior.AllowGet);
        }


        // GET: Personals/Details/5
        public ActionResult Details(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Personal personal = db.Personals.Find(id);
            if (personal == null)
            {
                return HttpNotFound();
            }
            return View(personal);
        }

        // POST: Personals/GenerateFakePersonals
        [HttpPost]
        public ActionResult GenerateFakePersonals(int limit)
        {
            try
            {
                var context = new HRDB();

                int currentCount = context.Personals.Count();   // Số dòng hiện tại
                int startIndex = currentCount + 1;
                int maxIndex = currentCount + limit;
                int inserted = 0;

                var faker = new Bogus.Faker<Personal>()
                    .RuleFor(p => p.Employee_ID, (f, p) => 1000 + startIndex + f.IndexFaker)
                    .RuleFor(p => p.First_Name, f => f.Name.FirstName())
                    .RuleFor(p => p.Last_Name, f => f.Name.LastName())
                    .RuleFor(p => p.Middle_Initial, f => f.Random.Char('A', 'Z').ToString())
                    .RuleFor(p => p.Address1, f => f.Address.StreetAddress())
                    .RuleFor(p => p.Address2, f => f.Address.SecondaryAddress())
                    .RuleFor(p => p.City, f => f.Address.City())
                    .RuleFor(p => p.State, f => f.Address.StateAbbr())
                    .RuleFor(p => p.Zip, f => Convert.ToDecimal(f.Random.Number(10000, 99999)))
                    .RuleFor(p => p.Email, f => f.Internet.Email())
                    .RuleFor(p => p.Phone_Number, f => f.Phone.PhoneNumber())
                    .RuleFor(p => p.Social_Security_Number, f => f.Random.Replace("###-##-####"))
                    .RuleFor(p => p.Drivers_License, f => f.Random.Replace("DL########"))
                    .RuleFor(p => p.Marital_Status, f => f.PickRandom("Single", "Married", "Divorced"))
                    .RuleFor(p => p.Gender, f => f.PickRandom(true, false))
                    .RuleFor(p => p.Shareholder_Status, f => f.Random.Bool()) // NOT NULL
                    .RuleFor(p => p.Benefit_Plans, f => (decimal?)null)
                    .RuleFor(p => p.Ethnicity, f => f.PickRandom("Asian", "White", "Black", "Latino", "Other"));

                context.Configuration.AutoDetectChangesEnabled = false;

                const int batchSize = 1000;
                for (int i = startIndex; i <= maxIndex; i += batchSize)
                {
                    var toGenerate = Math.Min(batchSize, maxIndex - i + 1);
                    var batch = faker.Generate(toGenerate);
                    context.Personals.AddRange(batch);
                    context.SaveChanges();
                    inserted += batch.Count;
                    System.Diagnostics.Debug.WriteLine($"✅ Inserted batch - Total so far: {inserted}");
                }

                context.Configuration.AutoDetectChangesEnabled = true;

                return Json(new { success = true, message = $"✅ Tạo thành công {inserted} bản ghi Personal!" });
            }
            catch (Exception ex)
            {
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner });
            }
        }




        // GET: Personals/Create
        public ActionResult Create()
        {
            ViewBag.Benefit_Plans = new SelectList(db.Benefit_Plans, "Benefit_Plan_ID", "Plan_Name");
            ViewBag.Employee_ID = new SelectList(db.Emergency_Contacts, "Employee_ID", "Emergency_Contact_Name");
            ViewBag.Employee_ID = new SelectList(db.Employments, "Employee_ID", "Employment_Status");
            return View();
        }

        // POST: Personals/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Employee_ID,First_Name,Last_Name,Middle_Initial,Address1,Address2,City,State,Zip,Email,Phone_Number,Social_Security_Number,Drivers_License,Marital_Status,Gender,Shareholder_Status,Benefit_Plans,Ethnicity")] Personal personal)
        {
            if (ModelState.IsValid)
            {
                db.Personals.Add(personal);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.Benefit_Plans = new SelectList(db.Benefit_Plans, "Benefit_Plan_ID", "Plan_Name", personal.Benefit_Plans);
            ViewBag.Employee_ID = new SelectList(db.Emergency_Contacts, "Employee_ID", "Emergency_Contact_Name", personal.Employee_ID);
            ViewBag.Employee_ID = new SelectList(db.Employments, "Employee_ID", "Employment_Status", personal.Employee_ID);
            return View(personal);
        }

        // GET: Personals/Edit/5
        public ActionResult Edit(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Personal personal = db.Personals.Find(id);
            if (personal == null)
            {
                return HttpNotFound();
            }
            ViewBag.Benefit_Plans = new SelectList(db.Benefit_Plans, "Benefit_Plan_ID", "Plan_Name", personal.Benefit_Plans);
            ViewBag.Employee_ID = new SelectList(db.Emergency_Contacts, "Employee_ID", "Emergency_Contact_Name", personal.Employee_ID);
            ViewBag.Employee_ID = new SelectList(db.Employments, "Employee_ID", "Employment_Status", personal.Employee_ID);
            return View(personal);
        }

        // POST: Personals/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Employee_ID,First_Name,Last_Name,Middle_Initial,Address1,Address2,City,State,Zip,Email,Phone_Number,Social_Security_Number,Drivers_License,Marital_Status,Gender,Shareholder_Status,Benefit_Plans,Ethnicity")] Personal personal)
        {
            if (ModelState.IsValid)
            {
                db.Entry(personal).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.Benefit_Plans = new SelectList(db.Benefit_Plans, "Benefit_Plan_ID", "Plan_Name", personal.Benefit_Plans);
            ViewBag.Employee_ID = new SelectList(db.Emergency_Contacts, "Employee_ID", "Emergency_Contact_Name", personal.Employee_ID);
            ViewBag.Employee_ID = new SelectList(db.Employments, "Employee_ID", "Employment_Status", personal.Employee_ID);
            return View(personal);
        }

        // GET: Personals/Delete/5
        public ActionResult Delete(decimal id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Personal personal = db.Personals.Find(id);
            if (personal == null)
            {
                return HttpNotFound();
            }
            return View(personal);
        }

        // POST: Personals/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(decimal id)
        {
            Personal personal = db.Personals.Find(id);
            db.Personals.Remove(personal);
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
