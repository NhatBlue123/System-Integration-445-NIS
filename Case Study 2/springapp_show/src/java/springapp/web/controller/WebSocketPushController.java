/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import springapp.web.model.MergedEmployeePersonal;

/**
 *
 * @author Bluez
 */
public class WebSocketPushController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/send") // khi client gọi /app/send
    public void sendTestMessage(MergedEmployeePersonal data) {
        messagingTemplate.convertAndSend("/topic/eperson", data);
    }

    // Hàm này gọi từ backend để push data sau khi thêm/sửa/xóa
    public void broadcastData(MergedEmployeePersonal data) {
        messagingTemplate.convertAndSend("/topic/eperson", data);
    }

    public void notifyClientsUpdate() {
        messagingTemplate.convertAndSend("/topic/eperson", "update");
    }
}
