using Microsoft.AspNet.SignalR;

public class PersonalHub : Hub
{
    public void NotifyUpdate()
    {
        var context = GlobalHost.ConnectionManager.GetHubContext<PersonalHub>();
        context.Clients.All.updatePersonalList();
    }
}
