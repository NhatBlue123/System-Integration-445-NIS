using System;
using System.Net.Http;
using System.Threading.Tasks;

public class CacheCleaner
{
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
