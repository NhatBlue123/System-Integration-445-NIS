/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import springapp.web.model.EPerson;

/**
 *
 * @author Bluez
 */
@Controller
public class EPersonSocketController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void bcMergeData(List<EPerson> data)
    {
        messagingTemplate.convertAndSend("/topic/eperson",data);
    }
}
