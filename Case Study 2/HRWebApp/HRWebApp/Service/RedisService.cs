using System;
using StackExchange.Redis;

public class RedisService
{
    private static Lazy<ConnectionMultiplexer> lazyConnection = new Lazy<ConnectionMultiplexer>(() =>
    {
        return ConnectionMultiplexer.Connect("localhost:6379"); 
    });

    public static ConnectionMultiplexer Connection => lazyConnection.Value;

    public static void DeleteCache(string key)
    {
        var db = Connection.GetDatabase();
        db.KeyDelete(key);
    }
}
