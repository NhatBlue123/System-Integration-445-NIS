/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import springapp.web.model.Employee;
//import springapp.web.model.EPerson;

/**
 *
 * @author bluez
 */
@Controller
public class EmployeeSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void bcMergeData(List<Employee> data)
    {
        messagingTemplate.convertAndSend("/topic/employee",data);
    }
}
