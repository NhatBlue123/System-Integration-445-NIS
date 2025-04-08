/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;
import springapp.web.dao.EmployeeDao;
import springapp.web.model.Employee;
/**
 *
 * @author KunPC
 */
@RestController // tra ve JSON
//@Controller tra ve HTML
@RequestMapping(value = "/admin")
public class EmployeeController {

    EmployeeDao edao = new EmployeeDao();
    
    @RequestMapping(value = {"/employee/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String value = "";
        if (user != null) {
            try {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                List listEmployees = session.createQuery("from Employee").list();
                model.addAttribute("listEmployees", listEmployees);
                session.getTransaction().commit();
                value = "admin/listEmployee";
            } catch (Exception e) {
                 value = "admin/listEmployee";
            }

        } else {
            model.addAttribute("user", new Users());
            value= "redirect:/admin/login.html";
        }
        return value;
    }
    @RequestMapping(value = {"employee/create"},consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> insertEmployee(@RequestBody Employee employee) {
        try {
            edao.insert(employee);
        return new ResponseEntity<>("chen thanh cong", HttpStatus.OK);
        } catch (Exception e) {
                    return new ResponseEntity<>("{\"error\": \"Chen that bai\"}", HttpStatus.EXPECTATION_FAILED);

        }
    } 
    @RequestMapping(value = {"employee/test"}, method = RequestMethod.GET)
    public ResponseEntity<String> test() {
        try {
            
        return new ResponseEntity<>("test thanh cong", HttpStatus.OK);
        } catch (Exception e) {
                    return new ResponseEntity<>("{\"error\": \"test that bai\"}", HttpStatus.EXPECTATION_FAILED);

        }
    }
    
@RequestMapping(value = "/employee/generate", method = RequestMethod.POST)
public ResponseEntity<String> generateEmployees() {
    try {
        List<Employee> list = new ArrayList<>();
        Random rand = new Random();

        // 👉 Lấy số lượng hiện có trong DB
        int currentCount = edao.getEmployeeCount();
        int startIndex = currentCount + 1;

        for (int i = startIndex; i < startIndex + 500000; i++) {
            Employee emp = new Employee();
            emp.setEmployeeNumber(1002 + i);
            emp.setIdEmployee(i);

            if (i <= startIndex + 125000) {
                emp.setFirstName("Nguyen " + i);
                emp.setLastName("Nhat " + i);
            } else if (i <= startIndex + 250000) {
                emp.setFirstName("Huynh " + i);
                emp.setLastName("Bao " + i);
            } else if (i <= startIndex + 375000) {
                emp.setFirstName("Tran " + i);
                emp.setLastName("Vu " + i);
            } else {
                emp.setFirstName("Phan " + i);
                emp.setLastName("Kiet " + i);
            }

            emp.setSsn(100000000L + i);
            emp.setPayRate(String.format("%.2f", rand.nextDouble() * 100));
            emp.setPayRatesId(rand.nextInt(5) + 1);
            emp.setVacationDays(rand.nextInt(30));
            emp.setPaidToDate((byte) (rand.nextInt(2)));
            emp.setPaidLastYear((byte) (rand.nextInt(2)));

            list.add(emp);

            if (list.size() == 1000) {
                edao.insertBatch(list);
                System.out.println("Inserted " + i + " employee");
                list.clear();
            }
        }

        if (!list.isEmpty()) {
            edao.insertBatch(list);
        }

        return new ResponseEntity<>("Tạo 500000 employee thành công", HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}




//@Controller
//@RequestMapping(value = "/admin")
//public class EmployeeController {
//    @RequestMapping(value = "/employee/list", method = RequestMethod.GET)
//    public String listEmployee(ModelMap model, HttpServletRequest request) {
//        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
//        String value = "";
//        if (user != null) {
//            try {
//                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//                session.beginTransaction();
//                List listEmployees = session.createQuery("from Employee").list();
//                model.addAttribute("listEmployees", listEmployees);
//                session.getTransaction().commit();
//                value = "admin/listEmployee";
//            } catch (Exception e) {
//            }
//
//        } else {
//            model.addAttribute("user", new Users());
//            value= "redirect:/admin/login.html";
//        }
//        return value;
//    }
//}
