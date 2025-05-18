using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(HRApp.Startup))]
namespace HRApp
{
    public class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            app.MapSignalR();
        }
    }
}
