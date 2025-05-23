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
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import springapp.web.model.Personal;

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

    @RequestMapping(value = "/employee/getEmployeeById/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id) {
        try {
            Employee employee = edao.getById(id);
            if (employee != null) {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(employee);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không tìm thấy nhân viên với ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi khi lấy thông tin nhân viên", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/employee/editEmployee/{id}"}, method = RequestMethod.GET)
    public String pageEditEmployee(@PathVariable("id") int id, ModelMap model) {
        try {
            System.out.println("called edit");
            Employee employee = edao.getById(id);
            //  System.out.println("id employee: " + employee.getIdEmployee());
            model.addAttribute("employee", employee);
        } catch (Exception e) {
            return "admin/error";
        }

        return "admin/editEmployee";
    }

    @RequestMapping(value = "/employee/updateEmployee", method = RequestMethod.POST)
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        try {
            System.out.println("called updated8888");
            System.out.println("last and firts name: " + employee.getLastName() + " " + employee.getFullName());
            edao.update(employee);
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
            //socketE.bcMergeData(list);
        } catch (Exception e) {
            return "admin/error";
        }
        return "redirect:/admin/employee/list.html";
    }

    @RequestMapping(value = "/employee/updateEmployeeEPerson", method = RequestMethod.POST)
    public String updateEmployeeEPerson(@RequestBody Employee employee) {
        try {
            System.out.println("called updated8888");
            System.out.println("last and firts name: " + employee.getLastName() + " " + employee.getFullName());
            edao.update(employee);
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
            //socketE.bcMergeData(list);
        } catch (Exception e) {
            return "admin/error";
        }
        return "redirect:/admin/employee/list.html";
    }

    // trang addEmployee
    @RequestMapping(value = {"/employee/addEmployee"}, method = RequestMethod.GET)
    public String addEmployee(ModelMap model, HttpServletRequest request) {
        model.addAttribute("employee", new Employee());
        return "admin/addEmployee";

    }

    private static final String CREATE_PERSONAL_API_URL = "http://localhost:19335/Personals/generateAPersonalFrompEmployee";

    public void createPersonalFromEmployee(Personal per) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper ob = new ObjectMapper();
            Map<String, Object> map = ob.convertValue(per, new TypeReference<Map<String, Object>>() {
            });
            map.remove("state");
            map.remove("email");
            map.remove("city");
            map.remove("zip");
            map.remove("gender");
            map.remove("address1");
            map.remove("full_Name");
            map.remove("address2");
            map.remove("last_Name");
            map.remove("employee_ID");
            map.remove("first_Name");
            map.remove("ethnicity");
            map.remove("Benefit_Plans");
            map.remove("phone_Number");
            map.remove("drivers_License");
            map.remove("shareholder_Status");
            map.remove("marital_Status");
            map.remove("benefit_Plans");
            map.remove("social_Security_Number");
            map.remove("middle_Initial");
            String jsonbody = ob.writeValueAsString(map);
            HttpEntity<String> entity = new HttpEntity<>(jsonbody, headers);
            System.out.println("ENTITY cuoi: " + entity);
            RestTemplate temp = new RestTemplate();
            temp.postForObject(CREATE_PERSONAL_API_URL, entity, String.class);
            System.out.println("Personal da duoc gui qua hr");
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }

    }

    //api them 1 employee của phần trang add Employe của spring
    @RequestMapping(value = "/employee/createEmployee", method = RequestMethod.POST)
    public String createEPerson(@ModelAttribute("employee") Employee employee) {
        System.out.println("Called from employee");
        Personal per = new Personal();
        try {
            List<Employee> list = new ArrayList<>();
            list.add(employee);
            edao.insertBatch(list);
            per.setEmployee_ID(employee.getIdEmployee());
            per.setFirst_Name(employee.getFirstName());
            per.setLast_Name(employee.getLastName());
            createPersonalFromEmployee(per);
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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // api tạo employee từ bên addEPerson của EPerson Management 
    @RequestMapping(value = "/employee/generateAEmployeeByEPerson", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployeeByEPerson(@RequestBody EPerson eperson) {
        try {
            List<Employee> list = new ArrayList<>();

            Employee emp = new Employee();

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
            return new ResponseEntity<>("Lỗi khi tạo employee: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    // nhan queue tu c#
    @RequestMapping(value = "/employee/generateAEmployeeFrompPersonal", method = RequestMethod.POST)
    public ResponseEntity<String> generateAEmployeeFrompPersonal(@RequestBody Employee employee) {
        try {

            Faker myF = new Faker(new Locale("en"));
            int currentCount = edao.getEmployeeCount();
            int startIndex = currentCount + 1;

            //if(employee.getFirstName() == "" || employee.getLastName() == "" )
            Employee e = new Employee();
            e.setEmployeeNumber(1000 + employee.getIdEmployee());
            e.setIdEmployee(employee.getIdEmployee());
            e.setFirstName(employee.getFirstName());
            e.setLastName(employee.getLastName());
            e.setSsn(100000000L + startIndex);

            edao.insert(e);
            clearEmployeeCache();

            try {
                RestTemplate rest = new RestTemplate();
                String cacheUrl = "http://localhost:8888/springapp_show/admin/EPerson/clearCache";
                rest.getForObject(cacheUrl, String.class);
                System.out.println("Da xoa cache");

            } catch (Exception ee) {
                System.err.println("Loi khi xoa cache" + ee.getMessage());
            }
            return new ResponseEntity<>("Tạo 1 employee thành công với ID = ", HttpStatus.OK);

        } catch (Exception ee) {
            ee.printStackTrace();
            return new ResponseEntity<>("Lỗi khi tạo employee or trùng id", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //  return new ResponseEntity<>("Tạo  " + "employee thành công", HttpStatus.OK);
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
