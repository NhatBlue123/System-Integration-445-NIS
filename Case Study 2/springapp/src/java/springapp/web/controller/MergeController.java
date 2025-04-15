/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springapp.web.model.MergedEmployeePersonal;
import springapp.web.model.Employee;
import springapp.web.model.Personal;
import java.util.Arrays;


/**
 *
 * @author Bluez
 */
@RestController
@RequestMapping("/api")
public class MergeController {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = {"/mergeEmployeePersonal"}, method = RequestMethod.GET)

    public List<MergedEmployeePersonal> getMergedData() {
        // 1. Gọi 2 API
        ResponseEntity<Employee[]> empResponse = restTemplate.getForEntity(
                "http://localhost:8888/springapp/admin/employee/getLimitEmployee/1000", Employee[].class);
        ResponseEntity<Personal[]> perResponse = restTemplate.getForEntity(
                "http://localhost:19335/Personals/getAllPersonal", Personal[].class);

        List<Employee> employees = Arrays.asList(empResponse.getBody());
        List<Personal> personals = Arrays.asList(perResponse.getBody());

        // 2. Chuyển personal thành Map theo id để dễ join
        Map<Integer, Personal> personalMap = personals.stream()
                .collect(Collectors.toMap(Personal::getEmployee_ID, p -> p));

        // 3. Gộp dữ liệu
        List<MergedEmployeePersonal> result = new ArrayList<>();
        for (Employee e : employees) {
            MergedEmployeePersonal merged = new MergedEmployeePersonal();
            merged.setIdEmployee(e.getIdEmployee());
            merged.setFirstName(e.getFirstName());
            merged.setLastName(e.getLastName());
            merged.setSsn(e.getSsn());
            merged.setPayRate(e.getPayRate());
            merged.setPayRatesId(e.getPayRatesId());
            merged.setVacationDays(e.getVacationDays());
            merged.setPaidToDate(e.getPaidToDate());
            merged.setPaidLastYear(e.getPaidLastYear());

            // private int employeeNumber;//
            //    private int idEmployee; //
            //    private String lastName;//
            //    private String firstName;//
            //    private long ssn;//
            //    private String payRate;
            //    private Integer payRatesId;
            //    private Integer vacationDays;
            //    private Byte paidToDate;
            //    private Byte paidLastYear;
            //    // hr
            //    private String Middle_Initial;//
            //    private String Address1;//
            //    private String Address2;//
            //    private String City;//
            //    private String State;//
            //    private int Zip;//
            //    private String Email;//
            //    private String Phone_Number;//
            //    private String Social_Security_Number;
            //    private String Drivers_License;//
            //    private String Marital_Status;//
            //    private boolean Gender;//
            //    private boolean Shareholder_Status;//
            Personal p = personalMap.get(e.getIdEmployee());
            if (p != null) {
                merged.setPhone_Number(p.getPhone_Number());
                merged.setAddress1(p.getAddress1());
                merged.setAddress2(p.getAddress2());
                merged.setMarital_Status(p.getMarital_Status());
                merged.setCity(p.getCity());
                merged.setState(p.getState());
                merged.setEmail(p.getEmail());
                merged.setSocial_Security_Number(p.getSocial_Security_Number());
                merged.setDrivers_License(p.getDrivers_License());
                merged.setMarital_Status(p.getMarital_Status());
                merged.setGender(p.isGender());
                merged.setShareholder_Status(p.isShareholder_Status());
                
            } else {
                merged.setPhone_Number(null);
                //merged.setAddress1(null);
                merged.setPhone_Number(null);
                merged.setAddress1(null);
                merged.setAddress2(null);
                merged.setMarital_Status(null);
                merged.setCity(null);
                merged.setState(null);
                merged.setEmail(p.getEmail());
                merged.setSocial_Security_Number(null);
                merged.setDrivers_License(null);
                merged.setMarital_Status(null);
                merged.setGender(true);
                merged.setShareholder_Status(true);
            }

            result.add(merged);
        }

        return result;
    }

}
