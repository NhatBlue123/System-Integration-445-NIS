using System;
using System.Text;
using System.Web.Mvc;

namespace HRWebApp.Controllers
{
    public class BlueController : Controller
    {
        [HttpGet]
        public ActionResult SSOLogin2(string token)
        {
            try
            {
                if (string.IsNullOrEmpty(token))
                    return new HttpStatusCodeResult(400, "Ko co token");

                // Giải mã bit of acii
                byte[] tokenBytes = Convert.FromBase64String(token);
                string decoded = Encoding.UTF8.GetString(tokenBytes);

                string[] parts = decoded.Split('|');
                if (parts.Length != 2) return new HttpStatusCodeResult(400, "dinh dang token loi");

                string username = parts[0];
                long timestamp;

                if (!long.TryParse(parts[1], out timestamp))
                    return new HttpStatusCodeResult(400, "thoi gian loi");

                // check time
                long now = DateTimeOffset.UtcNow.ToUnixTimeSeconds();
                if (now - timestamp > 300) return new HttpStatusCodeResult(400, "het han token ");

                // Lưu session
                Session["USER"] = username;
                Session["LOGIN_TIME"] = timestamp;
                //        ViewBag.Username = username;
                return View("Dashboard");
            }
            catch (FormatException)
            {
                return new HttpStatusCodeResult(400, "dinh dang token loi");
            }
            catch (Exception)
            {
                return new HttpStatusCodeResult(400, "Ko co token");
            }
        }
    }
}
