/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;
import springapp.web.dao.EmployeeDao;
import springapp.web.model.Employee;

/**
 *
 * @author KunPC
 */
//@CrossOrigin(origins = "http://localhost:8888")
@Controller // tra ve JSON
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
            value = "redirect:/admin/login.html";
        }
        return value;
    }

    @RequestMapping(value = {"employee/create"}, consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
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
    
    @RequestMapping(value = {"employee/deleteAllE"}, method = RequestMethod.GET)
    public ResponseEntity<String> deleteAllEs() {
        try {

            return new ResponseEntity<>("test thanh cong", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"error\": \"test that bai\"}", HttpStatus.EXPECTATION_FAILED);

        }
    }

    @RequestMapping(value = {"employee/getLimitEmployee/{limit}"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLimitEmployees(@PathVariable("limit") int limit) {
        List<Employee> employees = edao.listUserLimit(limit);
        if (employees != null && !employees.isEmpty()) {
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                json.append(String.format(
                        "{"
                        + "\"employeeNumber\": \"%d\", "
                        + "\"idEmployee\": \"%d\", "
                        + "\"firstName\": \"%s\", "
                        + "\"lastName\": \"%s\", "
                        + "\"ssn\": \"%d\", "
                        + "\"payRate\": \"%s\", "
                        + "\"payRatesId\": \"%d\", "
                        + "\"vacationDays\": \"%d\", "
                        + "\"paidToDate\": \"%d\", "
                        + "\"paidLastYear\": \"%d\""
                        + "}",
                        e.getEmployeeNumber(),
                        e.getIdEmployee(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getSsn(),
                        e.getPayRate(),
                        e.getPayRatesId(),
                        e.getVacationDays(),
                        e.getPaidToDate(),
                        e.getPaidLastYear()
                ));
                if (i != employees.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("[]", HttpStatus.OK);
        }

    }

    @RequestMapping(value = {"employee/getAllEmployee"}, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getAllEmployees() {
        List<Employee> employees = edao.listEmployee();
        if (employees != null && !employees.isEmpty()) {
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < employees.size(); i++) {
                Employee e = employees.get(i);
                json.append(String.format(
                        "{"
                        + "\"employeeNumber\": \"%d\", "
                        + "\"idEmployee\": \"%d\", "
                        + "\"firstName\": \"%s\", "
                        + "\"lastName\": \"%s\", "
                        + "\"fullName\": \"%s\", "
                        + "\"ssn\": \"%d\", "
                        + "\"payRate\": \"%s\", "
                        + "\"payRatesId\": \"%d\", "
                        + "\"vacationDays\": \"%d\", "
                        + "\"paidToDate\": \"%d\", "
                        + "\"paidLastYear\": \"%d\""
                        + "}",
                        e.getEmployeeNumber(),
                        e.getIdEmployee(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getFullName(),
                        e.getSsn(),
                        e.getPayRate(),
                        e.getPayRatesId(),
                        e.getVacationDays(),
                        e.getPaidToDate(),
                        e.getPaidLastYear()
                ));
                if (i != employees.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("[]", HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/employee/generateAEmployeeAndAPersonal", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployeeAndAPersonal() {
        Faker myF = new Faker(new Locale("en"));
        try {
            List<Employee> list = new ArrayList<>();
            Random rand = new Random();

            int currentCount = edao.getEmployeeCount();
            int startIndex = currentCount + 1;

            Employee emp = new Employee();
            emp.setEmployeeNumber(startIndex);
            emp.setIdEmployee(1000 + startIndex);

            emp.setFirstName(myF.name().firstName());
            emp.setLastName(myF.name().lastName());

            String hrApiUrl = "Personals/CreateAPersonalWithFirtsNameAndLastName/"
                    + "?firstName=" + emp.getFirstName()
                    + "&lastName=" + emp.getLastName();

            emp.setSsn(100000000L + startIndex);
            emp.setPayRate(String.format("%.2f", rand.nextDouble() * 100));
            emp.setPayRatesId(rand.nextInt(5) + 1);
            emp.setVacationDays(rand.nextInt(30));
            emp.setPaidToDate((byte) (rand.nextInt(2)));
            emp.setPaidLastYear((byte) (rand.nextInt(2)));

            list.add(emp);  // chỉ 1 employee

            edao.insertBatch(list);

            RestTemplate restTemplate = new RestTemplate();
            try {
                String result = restTemplate.postForObject(hrApiUrl, null, String.class);
                System.out.println("HR App response: " + result);
            } catch (Exception ex) {
                System.err.println("❌ Không thể gọi HR App: " + ex.getMessage());
            }

            return new ResponseEntity<>("Tạo 1 employee thành công với ID = " + emp.getIdEmployee(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/employee/generateAEmployee", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployee() {
        Faker myF = new Faker(new Locale("en"));
        try {
            List<Employee> list = new ArrayList<>();
            Random rand = new Random();

            int currentCount = edao.getEmployeeCount();
            int startIndex = currentCount + 1;

            Employee emp = new Employee();
            emp.setEmployeeNumber(startIndex);
            emp.setIdEmployee(1000 + startIndex);

            emp.setFirstName(myF.name().firstName());
            emp.setLastName(myF.name().lastName());

            emp.setSsn(100000000L + startIndex);
            emp.setPayRate(String.format("%.2f", rand.nextDouble() * 100));
            emp.setPayRatesId(rand.nextInt(5) + 1);
            emp.setVacationDays(rand.nextInt(30));
            emp.setPaidToDate((byte) (rand.nextInt(2)));
            emp.setPaidLastYear((byte) (rand.nextInt(2)));

            list.add(emp);  // chỉ 1 employee

            edao.insertBatch(list);

            return new ResponseEntity<>("Tạo 1 employee thành công với ID = " + emp.getIdEmployee(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/employee/generate/{limit}", method = RequestMethod.POST)
    public ResponseEntity<String> generateEmployeesLimit(@PathVariable("limit") int limit) {
        Faker myF = new Faker(new Locale("en"));
        try {
            List<Employee> list = new ArrayList<>();
            Random rand = new Random();

            //  Lấy số lượng hiện có trong DB
            // curren = 5
            int currentCount = edao.getEmployeeCount();
            // max = 10
            int maxIndex = currentCount + limit;
            // start 6
            int startIndex = currentCount + 1;
            int dem = 0;
            for (int i = startIndex; i < maxIndex + 1; i++) {

                Employee emp = new Employee();
                emp.setEmployeeNumber(1000 + i);
                emp.setIdEmployee(i);

                emp.setFirstName(myF.name().firstName());
                emp.setLastName(myF.name().lastName());

                emp.setSsn(100000000L + i);
                emp.setPayRate(String.format("%.2f", rand.nextDouble() * 100));
                emp.setPayRatesId(rand.nextInt(5) + 1);
                emp.setVacationDays(rand.nextInt(30));
                emp.setPaidToDate((byte) (rand.nextInt(2)));
                emp.setPaidLastYear((byte) (rand.nextInt(2)));
                dem++;
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

            return new ResponseEntity<>("Tạo " + dem + " employee thành công", HttpStatus.OK);
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
