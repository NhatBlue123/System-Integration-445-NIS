/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springapp.web.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import springapp.web.model.MergedEmployeePersonal;
import springapp.web.model.Personal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.Optional;

/**
 *
 * @author Bluez
 */
@RestController
@RequestMapping(value = "/api")
public class MergeController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ResponseEntity<String> test() {
        try {

            return new ResponseEntity<>("test thanh cong", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"error\": \"test that bai\"}", HttpStatus.EXPECTATION_FAILED);

        }
    }
//    @RequestMapping(value = "/EPerson/testGetEmployee1", method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity<Employee[]> testData() {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String url = "http://localhost:8080/springapp/admin/employee/getAllEmployee";
//            
//            // Lấy dữ liệu dưới dạng mảng Employee
//            Employee[] employees = restTemplate.getForObject(url, Employee[].class);
//            
//            // Trả về mảng đối tượng trực tiếp
//                return new ResponseEntity<>(employees, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @RequestMapping(value = {"/EPerson/GetAllEmployee"}, method = RequestMethod.GET)
    public ResponseEntity<String> GetAllEmployee() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/springapp/admin/employee/getAllEmployee";

            // Gọi API trả về chuỗi JSON
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            // Convert JSON string thành List<Employee>
            ObjectMapper mapper = new ObjectMapper();
            List<Employee> employees = mapper.readValue(response.getBody(), new TypeReference<List<Employee>>() {
            });

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

                    if (i < employees.size() - 1) {
                        json.append(",");
                    }
                }

                json.append("]");
                return new ResponseEntity<>(json.toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không có dữ liệu", HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = {"/EPerson/GetAllPersonal"}, method = RequestMethod.GET)
    public ResponseEntity<String> GetAllPersonal() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:19335/Personals/getAllPersonal";

            // Gọi API trả về chuỗi JSON
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            // Convert JSON string thành List<Employee>
            ObjectMapper mapper = new ObjectMapper();
            List<Personal> personals = mapper.readValue(response.getBody(), new TypeReference<List<Personal>>() {
            });

            if (personals != null && !personals.isEmpty()) {
                StringBuilder json = new StringBuilder("[");

//                    "Employee_ID"
//                    : 1001,
//        "First_Name": "Madge",
//        "Last_Name": "McClure",
//        "Middle_Initial": "W",
//        "Address1": "56332 Nienow Ferry",
//        "Address2": "Apt. 272",
//        "City": "Flatleybury",
//        "State": "MA",
//        "Zip": 57015,
//        "Email": "Cassidy_Mertz@yahoo.com",
//        "Phone_Number": "318-683-6271 x77069",
//        "Social_Security_Number": "276-34-1939",
//        "Drivers_License": "DL74168744",
//        "Marital_Status": "Single",
//        "Gender": false,
//        "Shareholder_Status": true,
//        "Benefit_Plans": null,
//        "Ethnicity": "Other"
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
                return new ResponseEntity<>(json.toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không có dữ liệu", HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Lỗi server: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   
// @RequestMapping(value = {"/EPerson/MergeData"}, method = RequestMethod.GET, produces = "application/json")
//public ResponseEntity<String> getMergedData() {
//    try {
//        // 1. Gọi lại 2 hàm đã viết
//        ResponseEntity<String> empResponse = GetAllEmployee();
//        ResponseEntity<String> perResponse = GetAllPersonal();
//
//        // 2. Kiểm tra nếu không có dữ liệu
//        if (empResponse.getBody() == null || perResponse.getBody() == null) {
//            return new ResponseEntity<>("[]", HttpStatus.NO_CONTENT);
//        }
//
//        // 3. Convert JSON -> List object
//        ObjectMapper mapper = new ObjectMapper();
//        List<Employee> employees = mapper.readValue(empResponse.getBody(), new TypeReference<List<Employee>>() {});
//        List<Personal> personals = mapper.readValue(perResponse.getBody(), new TypeReference<List<Personal>>() {});
//
//        // 4. Tạo map để join
//        Map<Integer, Personal> personalMap = personals.stream()
//                .collect(Collectors.toMap(Personal::getEmployee_ID, p -> p));
//
//        // 5. Gộp dữ liệu
//        List<MergedEmployeePersonal> mergedList = new ArrayList<>();
//        for (Employee e : employees) {
//            MergedEmployeePersonal merged = new MergedEmployeePersonal();
//            merged.setEmployeeNumber(e.getEmployeeNumber());
//            merged.setIdEmployee(e.getIdEmployee());
//            merged.setFirstName(e.getFirstName());
//            merged.setLastName(e.getLastName());
//            merged.setSsn(e.getSsn());
//            merged.setPayRate(e.getPayRate());
//            merged.setPayRatesId(e.getPayRatesId());
//            merged.setVacationDays(e.getVacationDays());
//            merged.setPaidToDate(e.getPaidToDate());
//            merged.setPaidLastYear(e.getPaidLastYear());
//
//            Personal p = personalMap.get(e.getIdEmployee());
//            if (p != null) {
//                merged.setMiddle_Initial(p.getMiddle_Initial());
//                merged.setAddress1(p.getAddress1());
//                merged.setAddress2(p.getAddress2());
//                merged.setCity(p.getCity());
//                merged.setState(p.getState());
//                merged.setZip(p.getZip());
//                merged.setEmail(p.getEmail());
//                merged.setPhone_Number(p.getPhone_Number());
//                merged.setSocial_Security_Number(p.getSocial_Security_Number());
//                merged.setDrivers_License(p.getDrivers_License());
//                merged.setMarital_Status(p.getMarital_Status());
//                merged.setGender(p.isGender());
//                merged.setShareholder_Status(p.isShareholder_Status());
//            }
//
//            mergedList.add(merged);
//        }
//
//        // 6. Trả về JSON
//        String json = mapper.writeValueAsString(mergedList);
//        return new ResponseEntity<>(json, HttpStatus.OK);
//
//    } catch (Exception ex) {
//        ex.printStackTrace();
//        return new ResponseEntity<>("Lỗi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

}
