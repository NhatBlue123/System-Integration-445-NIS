using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Reflection;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using Bogus;
using System.Net.Http;
using System.Threading.Tasks;
using HRWebApp.Models;
using System.Web.Mvc;
using Microsoft.AspNet.SignalR;
using System.Diagnostics;
using Newtonsoft.Json;
using System.Text;
//using System.Web.Mvc;


namespace HRWebApp.Controllers
{
    public class PersonalsController : Controller
    {
        private HRDB db = new HRDB();
        private CacheCleaner cacheCleaner = new CacheCleaner();

        // GET: Personals
        public ActionResult Index()
        {
            // db1 là một kết nối đến Redis và có thể được sử dụng để lưu trữ dữ liệu
            var db1 = RedisService.Connection.GetDatabase();
            string key = "personalList";
            List<Personal> personals;

            var cached = db1.StringGet(key);

            if (cached.IsNullOrEmpty)
            {
                // Nếu không có dữ liệu trong cache, lấy từ database
                personals = db.Personals.ToList();
                // Lưu vào cache với thời gian sống là 5 phút

                db1.StringSet(key, Newtonsoft.Json.JsonConvert.SerializeObject(personals), TimeSpan.FromMinutes(5));
            }
            else
            {
                // Nếu có dữ liệu trong cache, lấy từ cache
                personals = Newtonsoft.Json.JsonConvert.DeserializeObject<List<Personal>>(cached);
            }

            // var personals = db.Personals.Include(p => p.Benefit_Plans1).Include(p => p.Emergency_Contacts).Include(p => p.Employment);
            return View(personals);
        }

        //GET: Personals/getAllPersonal
        public JsonResult getAllPersonal()
        {

            var personals = db.Personals
           .Select(p => new {
               p.Employee_ID,
               p.First_Name,
               p.Last_Name,
               Full_Name = p.First_Name + " " + p.Last_Name,
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
               Full_Name = p.First_Name + " " + p.Last_Name,
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
        public JsonResult GetPersonalById(int id)
        {
            var personal = db.Personals
                .Where(p => p.Employee_ID == id)
                .Select(p => new {
                    p.Employee_ID,
                    p.First_Name,
                    p.Last_Name,
                    Full_Name = p.First_Name + " " + p.Last_Name,
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
                .FirstOrDefault();

            if (personal == null)
            {
                return Json(new { success = false, message = "Personal not found" }, JsonRequestBehavior.AllowGet);
            }

            return Json( personal, JsonRequestBehavior.AllowGet);
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


        // POST: Personals/CreateAPersonal
        [HttpPost]
        public ActionResult CreateAPersonal()
        {
            try
            {
                var context = new HRDB();
                int currentCount = context.Personals.Count();   // Số dòng hiện tại
                int startIndex = currentCount + 1;
                String name = "";

                var faker = new Bogus.Faker<Personal>()
                    .RuleFor(p => p.Employee_ID, (f, p) => startIndex + f.IndexFaker)

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
                // Generate a single Personal object
                var personal = faker.Generate(1).FirstOrDefault();
                context.Personals.Add(personal);
                context.SaveChanges();
                context.Configuration.AutoDetectChangesEnabled = true;
                name = personal.First_Name + " " + personal.Last_Name;
                decimal id = personal.Employee_ID;

                // Xóa cache
                RedisService.DeleteCache("personalList");

                //  Gửi thông báo realtime
                //var contextr = GlobalHost.ConnectionManager.GetHubContext<PersonalHub>();
                //contextr.Clients.All.updatePersonalList();
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());


                return Json(new { success = true, message = $"Tao thanh cong personal voi id = {id}" });

            }
            catch (Exception ex)
            {
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner });
            }
        }

        [HttpPost]
        public ActionResult generateAPersonalFrompEmployee(Personal personal)
        {
            var context = new HRDB();

            Personal per = new Personal();
            per.Employee_ID = personal.Employee_ID;
            per.Last_Name = personal.Last_Name;
            per.First_Name = personal.First_Name;

            context.Personals.Add(per);
            context.Configuration.AutoDetectChangesEnabled = true;
            context.SaveChanges();

            // Xóa cache
            RedisService.DeleteCache("personalList");
            //  Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());

            return Json(new { success = true, message = $"Tao thanh cong personal" });
            
        }

        // POST: Personals/CreateAPersonalByEPerson
        [HttpPost]
        public ActionResult CreateAPersonalByEPerson(EPerson eperson)
        {
            Console.WriteLine("Hello call from eperson");
            try
            {
                Personal per = new Personal();
                var context = new HRDB();
                per.Employee_ID = eperson.Employee_ID;
                per.First_Name = eperson.First_Name;
                per.Last_Name = eperson.Last_Name;
                per.Address1 = eperson.Address1;
                per.Address2 = eperson.Address2;
                per.City = eperson.City;
                per.State = eperson.State;
                per.Zip = eperson.Zip;
                per.Email = eperson.Email;
                per.Phone_Number = eperson.Phone_Number;
                per.Social_Security_Number  = eperson.Social_Security_Number;
                per.Drivers_License = eperson.Drivers_License;
                per.Marital_Status = eperson.Marital_Status; 
                per.Gender = eperson.Gender;
                per.Shareholder_Status = eperson.Shareholder_Status;
              
                per.Ethnicity = eperson.Ethnicity;
              
                
                context.Personals.Add(per);
                context.Configuration.AutoDetectChangesEnabled = true;
                context.SaveChanges();

                // Xóa cache
                RedisService.DeleteCache("personalList");
                //  Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());


                return Json(new { success = true, message = $"Tao thanh cong personal" });
            }
            catch (Exception ex)
            {
                Console.WriteLine("LOI SERVER: " + ex.ToString());
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner });
            }
        }

        // POST: Personals/clearCache
        public ActionResult clearCache()
        {
            // Xóa cache
            RedisService.DeleteCache("personalList");
            //  Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");
          //  Task.Run(async () => await ClearCacheAsync());
            return Json(new { success = true, message = "Đã xóa cache" });
        }

        [HttpPost]
        public ActionResult updatePersonal(Personal personal)
        {
            try
            {
                db.Entry(personal).State = EntityState.Modified;
                db.SaveChanges();

                // Xóa cache
                RedisService.DeleteCache("personalList");
                //  Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());

                return Json(personal); // hoặc return Json(new { success = true });
            }
            catch (Exception ex)
            {
                return new HttpStatusCodeResult(500, ex.Message);
            }
        }

        public ActionResult generateAEmployeeFromPersonal(Personal per)
        {
            

            // Gửi qua Spring App để tạo Employee tương ứng
            try
            {
                using (var client = new HttpClient())
                {
                    var employeeObj = new
                    {
                        idEmployee = per.Employee_ID,
                        firstName = per.First_Name,
                        lastName = per.Last_Name
                    };

                    string apiUrl = "http://localhost:8080/springapp/admin/employee/generateAEmployeeFrompPersonal";
                    var json = JsonConvert.SerializeObject(employeeObj);
                    var content = new StringContent(json, Encoding.UTF8, "application/json");

                    var result = client.PostAsync(apiUrl, content).Result;

                    if (result.IsSuccessStatusCode)
                    {
                        Console.WriteLine("Đã gửi Employee sang SpringApp thành công.");
                    }
                    else
                    {
                        Console.WriteLine("Lỗi khi gửi Employee sang SpringApp: " + result.StatusCode);
                    }
                }
            }
            catch (Exception ex)
            {
                return Json(new { success = false, message = $"Exception khi gửi Employee" });

              //  Console.WriteLine("Exception khi gửi Employee: " + ex.Message);
            }

            // Xóa cache
            RedisService.DeleteCache("personalList");
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());

            return Json(new { success = true, message = $"Tao thanh cong personal" });
        }




        // POST: Personals/CreateAPersonalById?id = 122
        [HttpPost]
        public ActionResult CreateAPersonalById(int id)
        {
            try
            {
                var context = new HRDB();
                //int currentCount = context.Personals.Count();   // Số dòng hiện tại
                //int startIndex = currentCount + 1;
                String name = "";

                var faker = new Bogus.Faker<Personal>()
                    .RuleFor(p => p.Employee_ID, id)
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
                var personal = faker.Generate(1).FirstOrDefault();
                context.Personals.Add(personal);
                context.SaveChanges();
                context.Configuration.AutoDetectChangesEnabled = true;
                name = personal.First_Name + " " + personal.Last_Name;
                decimal employeeId = personal.Employee_ID;

                // Xóa cache
                RedisService.DeleteCache("personalList");
                //  Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());

                return Json(new { success = true, message = $"Tao thanh cong personal voi id = {employeeId}" });
            }
            catch (Exception ex)
            {
                string error = "Loi trung id";
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner + " or " + error });
            }
        }


        // POST: Personals/CreateAPersonalWithFirtsNameAndLastName/firstName=John&lastName=Doe //something
        [HttpPost]
        public ActionResult CreateAPersonalWithFirtsNameAndLastName(String firstName, String lastName)
        {
            try
            {
                var context = new HRDB();
                int currentCount = context.Personals.Count();   // Số dòng hiện tại
                int startIndex = currentCount + 1;
                String name = "";

                var faker = new Bogus.Faker<Personal>()
                    .RuleFor(p => p.Employee_ID, (f, p) => startIndex + f.IndexFaker)

                    .RuleFor(p => p.First_Name, firstName)
                    .RuleFor(p => p.Last_Name, lastName)
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
                // Generate a single Personal object
                var personal = faker.Generate(1).FirstOrDefault();
                context.Personals.Add(personal);
                context.SaveChanges();
                context.Configuration.AutoDetectChangesEnabled = true;
                name = personal.First_Name + " " + personal.Last_Name;
                decimal id = personal.Employee_ID;
                return Json(new { success = false, message = $"Tao thanh cong personal voi userame = {name} va id = {id}" });

            }
            catch (Exception ex)
            {
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner });
            }
        }

        [HttpPost]
//[Route("Personals/DeleteAll")]
public JsonResult DeleteAllPersonals()
{
    try
    {
        db.Personals.RemoveRange(db.Personals);
        db.SaveChanges();

         // Xóa cache
         RedisService.DeleteCache("personalList");
                // Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());

        return Json(new { success = true, message = "Đã xoá tất cả Personal." }, JsonRequestBehavior.AllowGet);
    }
    catch (Exception ex)
    {
        return Json(new { success = false, error = ex.Message }, JsonRequestBehavior.AllowGet);
    }
}

        // POST: Personals/GenerateLimitPersonals/3
        [HttpPost]
        public ActionResult GenerateLimitPersonals(int limit)
        {
            try
            {
                var context = new HRDB();

                int currentCount = context.Personals.Count();   // Số dòng hiện tại
                int startIndex = currentCount + 1;
                int maxIndex = currentCount + limit;
                int inserted = 0;

                var faker = new Bogus.Faker<Personal>()
                    .RuleFor(p => p.Employee_ID, (f, p) => startIndex + f.IndexFaker)
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
                // Xóa cache
                RedisService.DeleteCache("personalList");
                // Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                Task.Run(async () => await ClearCacheAsync());

                return Json(new { success = true, message = $"✅ Tạo thành công {inserted}  Personals!" });
            }
            catch (Exception ex)
            {
                var inner = ex.InnerException?.Message ?? ex.Message;
                return Json(new { success = false, error = inner });
            }
        }

        [HttpPost]
        public ActionResult DeleteByEmployeeId(int id)
{
    try
    {
        var personal = db.Personals.FirstOrDefault(p => p.Employee_ID == id);
        if (personal == null)
        {
            return Json(new { success = false, message = $"Không tìm thấy Personal với ID = {id}" }, JsonRequestBehavior.AllowGet);
        }

        db.Personals.Remove(personal);
        db.SaveChanges();

                // Xóa cache
                RedisService.DeleteCache("personalList");
                // Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");

                return Json(new { success = true, message = $"Đã xoá Personal với ID = {id}" }, JsonRequestBehavior.AllowGet);
    }
    catch (Exception ex)
    {
        return Json(new { success = false, error = ex.Message }, JsonRequestBehavior.AllowGet);
    }
}





        // GET: Personals/Create
        public ActionResult Create()
        {
            ViewBag.Benefit_Plans = new SelectList(db.Benefit_Plans, "Benefit_Plan_ID", "Plan_Name");
            ViewBag.Employee_ID = new SelectList(db.Emergency_Contacts, "Employee_ID", "Emergency_Contact_Name");
            ViewBag.Employee_ID = new SelectList(db.Employments, "Employee_ID", "Employment_Status");
            // xóa cache
            
            RedisService.DeleteCache("personalList");
            // Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());

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
                // xóa cache
                RedisService.DeleteCache("personalList");
                // Gửi thông báo realtime
                WebSocketServerManager.Broadcast("new-personal");
               // Task.Run(async () => await GenerateAEmployeeFromPersonal(personal));


                Task.Run(async () => await ClearCacheAsync());

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
            // xóa cache
            RedisService.DeleteCache("personalList");
            // Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());

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
            // xóa cache
            RedisService.DeleteCache("personalList");
            // Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());
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
            // xóa cache
            RedisService.DeleteCache("personalList");
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());
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
            // xóa cache
            RedisService.DeleteCache("personalList");
            // Gửi thông báo realtime
            WebSocketServerManager.Broadcast("new-personal");

            Task.Run(async () => await ClearCacheAsync());
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

        public JsonResult GetPersonalsJson()
        {
            var data = db.Personals.Select(p => new {
                FullName = p.First_Name + " " + p.Last_Name,
                p.City,
                p.Email,
                p.Phone_Number,
                Gender = (p.Gender == true ? "Male" : "Female"),
                Shareholder = (p.Shareholder_Status == true ? "Yes" : "No"),
                p.Employee_ID
            }).ToList();

            return Json(data, JsonRequestBehavior.AllowGet);
        }


        // GET: /PersonalsApi/GetAll
        public JsonResult GetAll()
        {
            var personals = db.Personals.Select(p => new
            {
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
            }).ToList();

            return Json(personals, JsonRequestBehavior.AllowGet);
        }
    

    public static async Task ClearCacheAsync()
        {
            string url = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    HttpResponseMessage response = await client.GetAsync(url);

                    if (response.IsSuccessStatusCode)
                    {
                        Console.WriteLine("✅ Đã xoá cache thành công");
                    }
                    else
                    {
                        Console.WriteLine($"⚠️ Lỗi: Không thể xóa cache. Mã lỗi: {response.StatusCode}");
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("❌ Lỗi khi gọi API xóa cache: " + ex.Message);
            }
        }
    }
}
