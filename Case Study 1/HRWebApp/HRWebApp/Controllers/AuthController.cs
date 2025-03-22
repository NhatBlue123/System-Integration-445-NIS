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
                    return new HttpStatusCodeResult(400, "Không có token");

                byte[] tokenBytes = Convert.FromBase64String(token);
                string decoded = Encoding.UTF8.GetString(tokenBytes);

                string[] parts = decoded.Split('|');
                if (parts.Length != 2) return new HttpStatusCodeResult(400, "Định dạng token lỗi");

                string username = parts[0];
                if (!long.TryParse(parts[1], out long timestamp))
                    return new HttpStatusCodeResult(400, "Thời gian lỗi");

                long now = DateTimeOffset.UtcNow.ToUnixTimeSeconds();
                if (now - timestamp > 60) return new HttpStatusCodeResult(400, "Token đã hết hạn");

                Session["USER"] = username;
                Session["LOGIN_TIME"] = timestamp;

                return RedirectToAction("Index", "Admin");
            }
            catch (FormatException)
            {
                return new HttpStatusCodeResult(400, "Định dạng token lỗi");
            }
            catch (Exception)
            {
                return new HttpStatusCodeResult(400, "Lỗi xử lý token");
            }
        }
        
    }
}
