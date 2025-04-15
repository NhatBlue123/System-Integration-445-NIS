using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;
using HRWebApp.Models;

namespace HRWebApp.Controllers
{
    public class PersonalsApiController : Controller
    {
        private HRDB db = new HRDB();

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
    }
}
