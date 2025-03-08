using System;
using System.Text;
using System.Web.Mvc;

namespace HRWebApp.Controllers
{
    public class AuthController : Controller
    {
        [HttpGet]
        public ActionResult SSOLogin(string token)
        {
            try
            {
                if (string.IsNullOrEmpty(token))
                    return new HttpStatusCodeResult(400, "Missing token");

                // Giải mã bit of acii
                byte[] tokenBytes = Convert.FromBase64String(token);
                string decoded = Encoding.UTF8.GetString(tokenBytes);

                string[] parts = decoded.Split('|');
                if (parts.Length != 2) return new HttpStatusCodeResult(400, "Invalid token format");

                string username = parts[0];
                long timestamp;

                if (!long.TryParse(parts[1], out timestamp))
                    return new HttpStatusCodeResult(400, "Invalid timestamp");

                // check time
                long now = DateTimeOffset.UtcNow.ToUnixTimeSeconds();
                if (now - timestamp > 300) return new HttpStatusCodeResult(400, "Token expired");

                // Lưu session
                Session["USER"] = username;
                Session["LOGIN_TIME"] = timestamp;
        //        ViewBag.Username = username;
                return View("Dashboard");
            }
            catch (FormatException)
            {
                return new HttpStatusCodeResult(400, "Invalid token format");
            }
            catch (Exception)
            {
                return new HttpStatusCodeResult(400, "Invalid token");
            }
        }
    }
}
