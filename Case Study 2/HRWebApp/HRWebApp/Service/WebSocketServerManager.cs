using Fleck;
using System.Collections.Generic;

public static class WebSocketServerManager
{
    private static List<IWebSocketConnection> _allSockets = new List<IWebSocketConnection>();
    private static WebSocketServer _server;

    public static void Start()
    {
        FleckLog.Level = LogLevel.Warn;

        _server = new WebSocketServer("ws://0.0.0.0:8181");

        _server.Start(socket =>
        {
            socket.OnOpen = () =>
            {
                _allSockets.Add(socket);
                System.Diagnostics.Debug.WriteLine("🔌 Client connected");
            };

            socket.OnClose = () =>
            {
                _allSockets.Remove(socket);
                System.Diagnostics.Debug.WriteLine("❌ Client disconnected");
            };

            socket.OnMessage = message =>
            {
                System.Diagnostics.Debug.WriteLine("💬 Received message: " + message);
            };
        });
    }

    public static void Broadcast(string message)
    {
        foreach (var socket in _allSockets)
        {
            socket.Send(message);
        }
    }
}
