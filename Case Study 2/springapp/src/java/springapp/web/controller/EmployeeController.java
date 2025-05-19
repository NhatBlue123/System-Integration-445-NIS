/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import springapp.web.model.HibernateUtil;
import springapp.web.model.Users;
import springapp.web.dao.EmployeeDao;
import springapp.web.model.EPerson;
import springapp.web.model.Employee;
import config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

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
    Jedis jedis = RedisConfig.getJedis();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private EmployeeSocketController socketE;

    //JedisPool jedisPool = new JedisPool(RedisConfig.getHost(),RedisConfig.getPort());
    @RequestMapping(value = {"/employee/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model, HttpServletRequest request) {
        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
        String viewName;

        if (user != null) {
            try (Jedis jedis = RedisConfig.getJedis()) {
                List<Employee> listEmployees;
                System.out.println("Kết nối được với Redis");

                String cached = jedis.get("cacheEmployee");
                if (cached != null) {
                    listEmployees = objectMapper.readValue(cached, new TypeReference<List<Employee>>() {
                    });
                    System.out.println("Lấy danh sách từ cache Redis");
                } else {
                    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                    session.beginTransaction();
                    listEmployees = session.createQuery("from Employee").list();
                    session.getTransaction().commit();

                    jedis.set("cacheEmployee", objectMapper.writeValueAsString(listEmployees));
                    jedis.expire("cacheEmployee", 300);
                    System.out.println("Lấy từ DB và lưu vào Redis");
                }

                model.addAttribute("listEmployees", listEmployees);
                viewName = "admin/listEmployee";
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("listEmployees", new ArrayList<>());
                viewName = "admin/listEmployee";
                System.err.println("Lỗi khi lấy hoặc lưu cache Redis");
            }
        } else {
            model.addAttribute("user", new Users());
            viewName = "redirect:/admin/login.html";
        }

        return viewName;
    }

    @RequestMapping(value = {"/employee/editEmployee/{id}"}, method = RequestMethod.GET)
    public  String pageEditEmployee(@PathVariable("id") int id)
    {
        return "admin/editEmployee";
    }
    
    // trang addEmployee
    @RequestMapping(value = {"/employee/addEmployee"}, method = RequestMethod.GET)
    public String addEmployee(ModelMap model, HttpServletRequest request) {
        model.addAttribute("employee", new Employee());
        return "admin/addEmployee";

    }

    //api them 1 employee
    @RequestMapping(value = "/employee/createEmployee", method = RequestMethod.POST)
    public String createEPerson(@ModelAttribute("employee") Employee employee) {
        System.out.println("Called from employee");
        try {
            List<Employee> list = new ArrayList<>();
            list.add(employee);
            edao.insertBatch(list);
            clearEmployeeCache();
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            // updateRealTimeData();
            socketE.bcMergeData(list);
            return "redirect:/admin/employee/list.html";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("loi gi" + e.getMessage());
            return "error";
        }

    }

    @RequestMapping(value = "/employee/clearCache", method = RequestMethod.GET)
    @ResponseBody
    public String clearEmployeeCache() {
        try (Jedis jedis = RedisConfig.getJedis()) {
            jedis.del("cacheEmployee");
            updateRealTimeData();
            return "Đã xóa cache nhân viên.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi xóa cache.";
        }
    }

    @RequestMapping(value = {"employee/create"}, consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> insertEmployee(@RequestBody Employee employee) {
        try {
            List<Employee> list = new ArrayList<>();

            edao.insert(employee);
            clearEmployeeCache();
            //    socketE.bcMergeData(data);
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

//    @RequestMapping(value = "/employee/generateAEmployeeAndAPersonal", method = RequestMethod.POST)
//    public ResponseEntity<String> generateAEmployeeAndAPersonal() {
//        Faker myF = new Faker(new Locale("en"));
//        try {
//            List<Employee> list = new ArrayList<>();
//            Random rand = new Random();
//
//            int currentCount = edao.getEmployeeCount();
//            int startIndex = currentCount + 1;
//
//            Employee emp = new Employee();
//            emp.setEmployeeNumber(startIndex);
//            emp.setIdEmployee(1000 + startIndex);
//
//            emp.setFirstName(myF.name().firstName());
//            emp.setLastName(myF.name().lastName());
//
//            String hrApiUrl = "Personals/CreateAPersonalWithFirtsNameAndLastName/"
//                    + "?firstName=" + emp.getFirstName()
//                    + "&lastName=" + emp.getLastName();
//
//            emp.setSsn(100000000L + startIndex);
//            emp.setPayRate(String.format("%.2f", rand.nextDouble() * 100));
//            emp.setPayRatesId(rand.nextInt(5) + 1);
//            emp.setVacationDays(rand.nextInt(30));
//            emp.setPaidToDate((byte) (rand.nextInt(2)));
//            emp.setPaidLastYear((byte) (rand.nextInt(2)));
//
//            list.add(emp);  // chỉ 1 employee
//
//            edao.insertBatch(list);
//
//            RestTemplate restTemplate = new RestTemplate();
//            try {
//                String result = restTemplate.postForObject(hrApiUrl, null, String.class);
//                System.out.println("HR App response: " + result);
//            } catch (Exception ex) {
//                System.err.println("❌ Không thể gọi HR App: " + ex.getMessage());
//            }
//
//            return new ResponseEntity<>("Tạo 1 employee thành công với ID = " + emp.getIdEmployee(), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/employee/generateAEmployeeByEPerson", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployeeByEPerson(@RequestBody EPerson eperson) {
        try {
            List<Employee> list = new ArrayList<>();

            Employee emp = new Employee();
            if (edao.checkExistId(eperson.getIdEmployee())) {
                return new ResponseEntity<>("Lỗi khi tạo employee từ EPerson vì trùng id", HttpStatus.BAD_REQUEST);
            }

            emp.setIdEmployee(eperson.getIdEmployee());
            emp.setEmployeeNumber(eperson.getEmployeeNumber());
            emp.setFirstName(eperson.getFirstName());
            emp.setLastName(eperson.getLastName());
            emp.setSsn(eperson.getSsn());
            emp.setPayRate(eperson.getPayRate());
            emp.setPayRatesId(eperson.getPayRatesId());
            emp.setVacationDays(eperson.getVacationDays());
            emp.setPaidToDate(eperson.getPaidToDate());
            emp.setPaidLastYear(eperson.getPaidLastYear());
            list.add(emp);
            //   list.add(emp);
            //  edao.insert(emp);          
            edao.insertBatch(list);

            clearEmployeeCache();
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            //  updateRealTimeData();
            socketE.bcMergeData(list);
            return new ResponseEntity<>("Đã tạo employee từ EPerson", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi tạo employee từ EPerson", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            emp.setEmployeeNumber(1000 + startIndex);
            emp.setIdEmployee(startIndex);

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
            clearEmployeeCache();
            socketE.bcMergeData(list);
            // xử lý xóa cache khi thêm mới employee
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            // updateRealTimeData();
            socketE.bcMergeData(list);

            return new ResponseEntity<>("Tạo 1 employee thành công", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/employee/generateAEmployeeById/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployeeById(@PathVariable("id") int id) {
        Faker myF = new Faker(new Locale("en"));
        try {
            List<Employee> list = new ArrayList<>();
            Random rand = new Random();

            int currentCount = edao.getEmployeeCount();
            int startIndex = currentCount + 1;

            Employee emp = new Employee();
            emp.setEmployeeNumber(1000 + id);
            emp.setIdEmployee(id);

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
            clearEmployeeCache();
            // xử lý xóa cache khi thêm mới employee
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            //  updateRealTimeData();
            socketE.bcMergeData(list);

            return new ResponseEntity<>("Tạo 1 employee thành công với ID = " + emp.getIdEmployee(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee or trùng id", HttpStatus.INTERNAL_SERVER_ERROR);
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
            clearEmployeeCache();
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            socketE.bcMergeData(list);

            return new ResponseEntity<>("Tạo " + dem + " employee thành công", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"employee/deleteAll"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllEmployees() {
        try {
            edao.deleteAll();
            clearEmployeeCache();
            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            return new ResponseEntity<>("Đã xoá toàn bộ employee", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi xoá employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"employee/deleteEmployeeById/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id) {
        try {

            edao.deleteById(id);
            clearEmployeeCache();

            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception e) {
                System.err.println("Loi khi xoa cache" + e.getMessage());
            }
            return new ResponseEntity<>("Đã xoá employee với id = " + id + "", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi xoá employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateRealTimeData() {
        System.out.println("callerd");
        try (Jedis jedis = RedisConfig.getJedis()) {
            List<Employee> listEmployees;
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            listEmployees = session.createQuery("from Employee").list();
            session.getTransaction().commit();

            jedis.set("cacheEmployee", objectMapper.writeValueAsString(listEmployees));
            jedis.expire("cacheEmployee", 300);
            socketE.bcMergeData(listEmployees);
            //                model.addAttribute("listEmployees", listEmployees);

        } catch (Exception e) {
            e.printStackTrace();

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
