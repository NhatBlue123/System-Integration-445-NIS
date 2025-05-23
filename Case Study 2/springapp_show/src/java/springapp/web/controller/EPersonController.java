package springapp.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import springapp.web.model.Employee;
import springapp.web.model.EPerson;
import springapp.web.model.HibernateUtil;
import springapp.web.model.EPerson;
import springapp.web.model.Personal;
import springapp.web.model.Users;
import until.RedisConfig;

@Controller
@RequestMapping(value = "/admin")
public class EPersonController {

    private static final String EMPLOYEE_API_URL = "http://localhost:8080/springapp/admin/employee/getAllEmployee";
    private static final String PERSONAL_API_URL = "http://localhost:19335/Personals/getAllPersonal";

    private static final String CREATE_EMPLOYEE_API_URL = "http://localhost:8080/springapp/admin/employee/generateAEmployeeByEPerson";
    private static final String CREATE_PERSONAL_API_URL = "http://localhost:19335/Personals/CreateAPersonalByEPerson";

    private static final String GET_EMPLOYEE_BY_ID_API_URL = "http://localhost:8080/springapp/admin/employee/getEmployeeById";
    private static final String GET_PERSONAL_BY_ID_API_URL = "http://localhost:19335/Personals/GetPersonalById";

    private static final String CLEAR_CACHE_EMPLOYEE_API_URL = "http://localhost:8080/springapp/admin/employee/clearCache";
    private static final String CLEAR_CACHE_PERSONAL_API_URL = "http://localhost:19335/Personals/clearCache";

    private static final String UPDATE_EMPLOYEE_API_URL = "http://localhost:8080/springapp/admin/employee/updateEmployeeEPerson";
    private static final String UPDATE_PERSONAL_API_URL = "http://localhost:19335/Personals/updatePersonal";

    private static final String DELETE_EMPLOYEE_API_URL = "http://localhost:8080/springapp/admin/employee/deleteEmployeeById";
    private static final String DELETE_PERSONAL_API_URL = "http://localhost:19335/Personals/DeleteByEmployeeId";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private EPersonSocketController socketC;

    @RequestMapping(value = "/EPerson", method = RequestMethod.GET)

    public String listEPersonByIdJedis(ModelMap model, HttpServletRequest request) {
        try (Jedis jedis = RedisConfig.getJedis()) {
            System.out.println("Ket noi duoc jedis");

            String cached = jedis.get("mergedEPerson");
            List<EPerson> mergedList = new ArrayList<>();

            // nếu cached có thì hiển thị lên table
            if (cached != null) {
                mergedList = objectMapper.readValue(cached, new TypeReference<List<EPerson>>() {
                });
                //cho dữ liệu vào listMerge
                model.addAttribute("listMerge", mergedList);

                System.out.println("OK CACHED");
            } // 
            else {

                List<Employee> employees = fetchEmployees();
                List<Personal> personals = fetchPersonals();

                // Tạo map từ Personal theo ID
                Map<Integer, Personal> personalMap = personals.stream()
                        .collect(Collectors.toMap(Personal::getEmployee_ID, p -> p));

                // Merge dữ liệu
                Set<Integer> allIds = new HashSet<Integer>();
                employees.forEach(e -> allIds.add(e.getIdEmployee()));
                personals.forEach(p -> allIds.add(p.getEmployee_ID()));

                for (Integer id : allIds) {
                    Employee emp = employees.stream()
                            .filter(e -> e.getIdEmployee() == id)
                            .findFirst().orElse(null);

                    Personal per = personalMap.get(id);

                    EPerson merged = new EPerson();
                    if (emp != null) {
                        merged.setIdEmployee(emp.getIdEmployee());
                        merged.setEmployeeNumber(emp.getEmployeeNumber());
                        merged.setFirstName(emp.getFirstName());
                        merged.setLastName(emp.getLastName());
                        merged.setSsn(emp.getSsn());
                        merged.setPayRate(emp.getPayRate());
                        merged.setPayRatesId(emp.getPayRatesId());
                        merged.setVacationDays(emp.getVacationDays());
                        merged.setPaidToDate(emp.getPaidToDate());
                        merged.setPaidLastYear(emp.getPaidLastYear());
                    }

                    if (per != null) {
                        merged.setMiddle_Initial(per.getMiddle_Initial());
                        merged.setIdEmployee(per.getEmployee_ID());
                        merged.setFirstName(per.getFirst_Name());
                        merged.setLastName(per.getLast_Name());
                        System.out.println("firtsname personal" + merged.getFirst_Name());
                        System.out.println("lastname personal" + merged.getLast_Name());
                        merged.setAddress1(per.getAddress1());
                        merged.setAddress2(per.getAddress2());
                        merged.setCity(per.getCity());
                        merged.setState(per.getState());
                        merged.setZip(per.getZip());
                        merged.setEmail(per.getEmail());
                        merged.setPhone_Number(per.getPhone_Number());
                        merged.setSocial_Security_Number(per.getSocial_Security_Number());
                        merged.setDrivers_License(per.getDrivers_License());
                        merged.setMarital_Status(per.getMarital_Status());
                        merged.setGender(per.isGender());
                        merged.setShareholder_Status(per.isShareholder_Status());
                        merged.setBenefit_Plans(per.getBenefit_Plans());
                        merged.setEthnicity(per.getEthnicity());
                    }
                    if (emp == null) {
                        merged.setFirstName(per.getFirst_Name());
                        merged.setLastName(per.getLast_Name());
                    } else {
                        merged.setFirstName(emp.getFirstName());
                        merged.setLastName(emp.getLastName());
                    }

                    mergedList.add(merged);
                }

                jedis.set("mergedEPerson", objectMapper.writeValueAsString(mergedList));
                jedis.expire("mergedEPerson", 300);
                System.out.println("Da luu vao cache");

            }

//            System.out.println("== Merged List ==");
//            for (EPerson m : mergedList) {
//                System.out.println("ID: " + m.getIdEmployee()
//                        + ", FirstName: " + m.getFirstName()
//                        + ", MiddleInitial: " + m.getMiddle_Initial());
//            }
            model.addAttribute("listMerge", mergedList);

        } catch (Exception e) {
            System.err.println("Khong ket noi duoc jedis");
            e.printStackTrace();
            model.addAttribute("listMerge", new ArrayList<>()); // fallback
        }

        return "admin/EPerson";
    }

    @RequestMapping(value = {"/EPerson/editEPerson/{id}"}, method = RequestMethod.GET)
    public String pageEditEPerson(@PathVariable("id") int id, ModelMap model) {
        try {
            Employee em = fetchEmployeesId(id);
            Personal pe = fetchPersonalsId(id);
//            System.out.println("employee: " + em.getFirstName() + "" + em.getLastName());
//            System.out.println("personal: " + pe.getFirst_Name()+ "" + pe.getLast_Name());

            System.out.println("called edit");
            EPerson e = new EPerson();
            //employww
            e.setIdEmployee(em.getIdEmployee());
            e.setEmployeeNumber(em.getEmployeeNumber());
            e.setFirstName(em.getFirstName());
            e.setLastName(em.getLastName());
            e.setSsn(em.getSsn());
            e.setPayRate(em.getPayRate());
            e.setPayRatesId(em.getPayRatesId());
            e.setVacationDays(em.getVacationDays());
            e.setPaidLastYear(em.getPaidLastYear());
            e.setPaidToDate(em.getPaidToDate());
            //personal
            e.setEmployee_ID(pe.getEmployee_ID());
            e.setFirst_Name(pe.getFirst_Name());
            e.setLast_Name(pe.getLast_Name());
            e.setMiddle_Initial(pe.getMiddle_Initial());
            e.setAddress1(pe.getAddress1());
            e.setAddress2(pe.getAddress2());
            e.setCity(pe.getCity());
            e.setState(pe.getState());
            e.setZip(pe.getZip());
            e.setPhone_Number(pe.getPhone_Number());
            e.setEmail(pe.getEmail());
            e.setSocial_Security_Number(pe.getSocial_Security_Number());
            e.setDrivers_License(pe.getDrivers_License());
            e.setMarital_Status(pe.getMarital_Status());
            e.setGender(pe.isGender());
            e.setMarital_Status(pe.getMarital_Status());
            e.setEthnicity(pe.getEthnicity());
            model.addAttribute("eperson", e);

        } catch (Exception e) {
            return "admin/error";
        }

        return "admin/editEPerson";
    }

    private Employee fetchEmployeesId(int id) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    GET_EMPLOYEE_BY_ID_API_URL + "/" + id,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<Employee>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi sever personal");
            return null; // fallback

        }
    }

    private Personal fetchPersonalsId(int id) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    GET_PERSONAL_BY_ID_API_URL + "/" + id,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<Personal>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null; // fallback
        }
    }

    public void clearCacheEmployee() {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getForObject(CLEAR_CACHE_EMPLOYEE_API_URL, null, String.class);
            System.out.println("Da xoa cache employee");

        } catch (Exception e) {
            System.err.println("Loi khi xoa cache" + e.getMessage());
        }
    }

    public void clearCachePersonal() {
        try {
            RestTemplate rest = new RestTemplate();
            rest.postForObject(CLEAR_CACHE_PERSONAL_API_URL, null, String.class);
            System.out.println("Da xoa cache persoal");

        } catch (Exception e) {
            System.err.println("Loi khi xoa cache" + e.getMessage());
        }
    }


    @RequestMapping(value = "/EPerson/updateEPerson", method = RequestMethod.POST)
    public String updateEmployee(@ModelAttribute("eperson") EPerson eperson) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper ob = new ObjectMapper();
            System.out.println("called updated");
            System.out.println("em id:" + eperson.getIdEmployee() + " fname" + eperson.getFirstName() + "lname" + eperson.getLastName());
            System.out.println("per name" + eperson.getFirstName());
            Employee em = new Employee(eperson.getEmployeeNumber(), eperson.getIdEmployee(), eperson.getLastName(), eperson.getFirstName(), eperson.getSsn(), eperson.getPayRate(), eperson.getPayRatesId(), eperson.getVacationDays(), eperson.getPaidLastYear(), eperson.getPaidToDate());
            Personal pe = new Personal(eperson.getEmployee_ID(), eperson.getFirst_Name(), eperson.getLast_Name(), eperson.getBenefit_Plans(), eperson.getMiddle_Initial(), eperson.getAddress1(), eperson.getAddress2(), eperson.getCity(), eperson.getState(), eperson.getZip(), eperson.getEmail(), eperson.getPhone_Number(), eperson.getSocial_Security_Number(), eperson.getDrivers_License(), eperson.getMarital_Status(), eperson.isGender(), eperson.isShareholder_Status(), eperson.getEthnicity());

            Map<String, Object> map = ob.convertValue(pe, new TypeReference<Map<String, Object>>() {
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
            String jsonebody = ob.writeValueAsString(em);

            HttpEntity<String> entity = new HttpEntity<>(jsonbody, headers);
            HttpEntity<String> entityE = new HttpEntity<>(jsonebody, headers);

            System.out.println("test cuoi e" + entityE);
            RestTemplate temp = new RestTemplate();
            temp.postForObject(UPDATE_EMPLOYEE_API_URL, entityE, String.class);
            temp.postForObject(UPDATE_PERSONAL_API_URL, entity, String.class);

            clearCacheEmployee();
            clearCachePersonal();
            // updateRealTimeData();
            //socketE.bcMergeData(list);
        } catch (Exception e) {
            return "admin/error";
        }
        return "redirect:/admin/EPerson";
    }

    @RequestMapping(value = "/EPerson/clearCache", method = RequestMethod.GET)
    @ResponseBody
    public String clearCache() {
        try (Jedis jedis = RedisConfig.getJedis()) {
            jedis.del("mergedEPerson");
            updateRealtimeMergeData();
            return "Đã xóa cache mergedEPerson và đẩy realTime";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi xóa cache";
        }
    }

    @RequestMapping(value = {"EPerson/deleteEPersonById/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id) {
        try {
            RestTemplate temp = new RestTemplate();
            temp.delete(DELETE_EMPLOYEE_API_URL, id);
            temp.delete(DELETE_PERSONAL_API_URL, id);
            return new ResponseEntity<>("Đã xoá EPerson với id = " + id + "", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi khi xoá employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // page
    @RequestMapping(value = "/EPerson/addEPerson", method = RequestMethod.GET)
    public String addEPerson(ModelMap model, HttpServletRequest request) {
        model.addAttribute("eperson", new EPerson());
        return "admin/addEPerson";
    }

    @RequestMapping(value = "/EPerson/createEPerson", method = RequestMethod.POST, produces = "application/json")
    public String createEPerson(@ModelAttribute("eperson") EPerson eperson) {
        System.out.println("Called from eperon");
        try {
            ObjectMapper ob = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> map = ob.convertValue(eperson, new TypeReference<Map<String, Object>>() {
            });
            map.remove("state");
            map.remove("email");
            map.remove("city");
            map.remove("zip");
            map.remove("gender");
            map.remove("address1");
            map.remove("address2");
            map.remove("last_Name");
            map.remove("employee_ID");
            map.remove("first_Name");
            map.remove("ethnicity");
            map.remove("phone_Number");
            map.remove("drivers_License");
            map.remove("shareholder_Status");
            map.remove("marital_Status");
            map.remove("benefit_Plans");
            map.remove("social_Security_Number");
            map.remove("middle_Initial");
            String jsonbody = ob.writeValueAsString(map);

            HttpEntity<String> entity = new HttpEntity<>(jsonbody, headers);
            System.out.println("test cuoi " + entity);
            System.out.println("DEBUG GUI DI");
            //    System.out.println("test object" + ob.writeValueAsString(eperson));
            if (eperson == null) {
                return "admin/error";

            }
            ResponseEntity<String> employeeResponse = restTemplate.postForEntity(CREATE_EMPLOYEE_API_URL, eperson, String.class);
            ResponseEntity<String> personalResponse = restTemplate.postForEntity(CREATE_PERSONAL_API_URL, entity, String.class);
            updateRealtimeMergeData();
            return "redirect:/admin/EPerson";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("loi gi" + e.getMessage());
            return "admin/error";
        }

    }

    public String listEPersonByNAME(ModelMap model, HttpServletRequest request) {
        try {
            List<Employee> employees = fetchEmployees();
            List<Personal> personals = fetchPersonals();

            // Tạo map Personal theo fullName
            Map<String, Personal> personalMap = personals.stream()
                    .collect(Collectors.toMap(Personal::getFull_Name, p -> p, (p1, p2) -> p1)); // nếu trùng thì giữ p1

            List<EPerson> mergedList = new ArrayList<>();

            Set<String> allFullNames = new HashSet<>();
            employees.forEach(e -> allFullNames.add(e.getFullName()));
            personals.forEach(p -> allFullNames.add(p.getFull_Name()));

            for (String fullName : allFullNames) {
                Employee emp = employees.stream()
                        .filter(e -> e.getFullName().equals(fullName))
                        .findFirst().orElse(null);

                Personal per = personalMap.get(fullName);

                EPerson merged = new EPerson();

                if (emp != null) {
                    merged.setIdEmployee(emp.getIdEmployee());
                    merged.setEmployeeNumber(emp.getEmployeeNumber());
                    merged.setFirstName(emp.getFirstName());
                    merged.setLastName(emp.getLastName());
                    merged.setSsn(emp.getSsn());
                    merged.setPayRate(emp.getPayRate());
                    merged.setPayRatesId(emp.getPayRatesId());
                    merged.setVacationDays(emp.getVacationDays());
                    merged.setPaidToDate(emp.getPaidToDate());
                    merged.setPaidLastYear(emp.getPaidLastYear());
                    merged.setFullName(emp.getFullName());
                }

                if (per != null) {
                    merged.setMiddle_Initial(per.getMiddle_Initial());
                    merged.setAddress1(per.getAddress1());
                    merged.setAddress2(per.getAddress2());
                    merged.setCity(per.getCity());
                    merged.setState(per.getState());
                    merged.setZip(per.getZip());
                    merged.setEmail(per.getEmail());
                    merged.setPhone_Number(per.getPhone_Number());
                    merged.setSocial_Security_Number(per.getSocial_Security_Number());
                    merged.setDrivers_License(per.getDrivers_License());
                    merged.setMarital_Status(per.getMarital_Status());
                    merged.setGender(per.isGender());
                    merged.setShareholder_Status(per.isShareholder_Status());
                    merged.setBenefit_Plans(per.getBenefit_Plans());
                    merged.setEthnicity(per.getEthnicity());
                    merged.setFullName(per.getFull_Name()); // nếu emp null
                }

                mergedList.add(merged);

            }

            model.addAttribute("listMerge", mergedList);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("listMerge", new ArrayList<>());
        }

        return "admin/EPerson";
    }

//    @RequestMapping(value = "/EPerson", method = RequestMethod.GET)
//    public String listEPerson(ModelMap model, HttpServletRequest request) {
//        Users user = (Users) request.getSession().getAttribute("LOGGEDIN_USER");
//
//        try {
//            List<Employee> employees = fetchEmployees();
//
//            model.addAttribute("listMerge", employees);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("listMerge", new ArrayList<>()); // fallback
//        }
//
//        return "admin/EPerson";
//    }
    @RequestMapping(value = "/EPerson/GetAllEmployee", method = RequestMethod.GET)
    public ResponseEntity<String> getAllEmployee() {
        try {
            List<Employee> employees = fetchEmployees();

            if (employees.isEmpty()) {
                return new ResponseEntity<>("Không có dữ liệu", HttpStatus.NO_CONTENT);
            }

            String json = buildEmployeeJson(employees);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/EPerson/GetAllPersonal", method = RequestMethod.GET)
    public ResponseEntity<String> getAllPersonal() {
        try {
            List<Personal> personals = fetchPersonals();

            if (personals.isEmpty()) {
                return new ResponseEntity<>("Không có dữ liệu", HttpStatus.NO_CONTENT);
            }

            String json = buildPersonalJson(personals);
            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi sever employee");

            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private List<Employee> fetchEmployees() {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    EMPLOYEE_API_URL,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<List<Employee>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi sever personal");
            return new ArrayList<>(); // fallback

        }
    }

    private List<Personal> fetchPersonals() {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    PERSONAL_API_URL,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), new TypeReference<List<Personal>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // fallback
        }
    }

    private String buildEmployeeJson(List<Employee> employees) {
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
            if (i < employees.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private String buildPersonalJson(List<Personal> personals) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < personals.size(); i++) {
            Personal e = personals.get(i);
            json.append(String.format(
                    "{"
                    + "\"Employee_ID\": \"%d\", "
                    + "\"First_Name\": \"%s\", "
                    + "\"Last_Name\": \"%s\", "
                    + "\"Middle_Initial\": \"%s\", "
                    + "\"Address1\": \"%s\", "
                    + "\"Address2\": \"%s\", "
                    + "\"City\": \"%s\", "
                    + "\"State\": \"%s\", "
                    + "\"Zip\": \"%d\", "
                    + "\"Email\": \"%s\","
                    + "\"Phone_Number\": \"%s\", "
                    + "\"Social_Security_Number\": \"%s\", "
                    + "\"Drivers_License\": \"%s\", "
                    + "\"Marital_Status\": \"%s\", "
                    + "\"Gender\": \"%b\", "
                    + "\"Shareholder_Status\": \"%b\", "
                    + "\"Benefit_Plans\": \"%d\", "
                    + "\"Ethnicity\": \"%s\", "
                    + "}",
                    e.getEmployee_ID(),
                    e.getFirst_Name(),
                    e.getLast_Name(),
                    e.getMiddle_Initial(),
                    e.getAddress1(),
                    e.getAddress2(),
                    e.getCity(),
                    e.getState(),
                    e.getZip(),
                    e.getEmail(),
                    e.getPhone_Number(),
                    e.getSocial_Security_Number(),
                    e.getDrivers_License(),
                    e.getMarital_Status(),
                    e.isGender(),
                    e.isShareholder_Status(),
                    e.getBenefit_Plans(),
                    e.getEthnicity()
            ));

            if (i < personals.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public void updateRealtimeMergeData() {
        try (Jedis jedis = RedisConfig.getJedis()) {
            List<Employee> employees = fetchEmployees();
            List<Personal> personals = fetchPersonals();

            Map<Integer, Personal> personalMap = personals.stream()
                    .collect(Collectors.toMap(Personal::getEmployee_ID, p -> p));

            Set<Integer> allIds = new HashSet<>();
            employees.forEach(e -> allIds.add(e.getIdEmployee()));
            personals.forEach(p -> allIds.add(p.getEmployee_ID()));

            List<EPerson> mergedList = new ArrayList<>();

            for (Integer id : allIds) {
                Employee emp = employees.stream().filter(e -> e.getIdEmployee() == id).findFirst().orElse(null);
                Personal per = personalMap.get(id);

                EPerson merged = new EPerson();
                if (emp != null) {
                    merged.setIdEmployee(emp.getIdEmployee());
                    merged.setEmployeeNumber(emp.getEmployeeNumber());
                    merged.setFirstName(emp.getFirstName());
                    merged.setLastName(emp.getLastName());
                    merged.setSsn(emp.getSsn());
                    merged.setPayRate(emp.getPayRate());
                    merged.setPayRatesId(emp.getPayRatesId());
                    merged.setVacationDays(emp.getVacationDays());
                    merged.setPaidToDate(emp.getPaidToDate());
                    merged.setPaidLastYear(emp.getPaidLastYear());
                }

                if (per != null) {
                    merged.setMiddle_Initial(per.getMiddle_Initial());
                    merged.setIdEmployee(per.getEmployee_ID());
                    merged.setAddress1(per.getAddress1());
                    merged.setAddress2(per.getAddress2());
                    merged.setFirstName(per.getFirst_Name());
                    merged.setLastName(per.getLast_Name());
                    System.out.println("firtsname personal" + merged.getFirst_Name());
                    System.out.println("lastname personal" + merged.getLast_Name());
                    merged.setCity(per.getCity());
                    merged.setState(per.getState());
                    merged.setZip(per.getZip());
                    merged.setEmail(per.getEmail());
                    merged.setPhone_Number(per.getPhone_Number());
                    merged.setSocial_Security_Number(per.getSocial_Security_Number());
                    merged.setDrivers_License(per.getDrivers_License());
                    merged.setMarital_Status(per.getMarital_Status());
                    merged.setGender(per.isGender());
                    merged.setShareholder_Status(per.isShareholder_Status());
                    merged.setBenefit_Plans(per.getBenefit_Plans());
                    merged.setEthnicity(per.getEthnicity());
                }
                if (emp != null && per != null) {
                    merged.setFirstName(emp.getFirstName() + "(" + per.getFirst_Name() + ")");
                    merged.setLastName(emp.getLastName() + "(" + per.getLast_Name() + ")");

                }

                mergedList.add(merged);
            }
//            System.out.println("== Merged List real time ==");
//            for (EPerson m : mergedList) {
//                System.out.println("ID: " + m.getIdEmployee()
//                        + ", FirstName: " + m.getFirstName()
//                        + ", MiddleInitial: " + m.getMiddle_Initial()
//                        + ", "
//                );
//            }

            jedis.set("mergedEPerson", objectMapper.writeValueAsString(mergedList));
            jedis.expire("mergedEPerson", 300);

            socketC.bcMergeData(mergedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = "/EPerson/realtime-update", method = RequestMethod.GET)
//    @ResponseBody
//    public String updateAndPushWebSocket() {
//        updateRealtimeMergeData();
//        return "Đã merge và đẩy xuống socket!";
//    }
}
