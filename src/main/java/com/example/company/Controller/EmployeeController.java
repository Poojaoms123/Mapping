package com.example.company.Controller;

import com.example.company.Model.Employee;
import com.example.company.Model.Response.EntityResponse;
import com.example.company.Model.SaveRequest.SaveEmployeeRequest;
import com.example.company.Service.EmployeeService;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    @GetMapping("/firstapi")
    public String firstapi(){
        return "its working";
    }
    @PostMapping("/saveOrUpdateEmployee")
    public ResponseEntity<?>saveOrUpdateEmployee(@RequestBody SaveEmployeeRequest saveEmployeeRequest,
                                                 @RequestParam Long companyId)throws Exception {
        try {
            return new ResponseEntity<>(new EntityResponse(employeeService.saveOrUpdateEmployee(saveEmployeeRequest,companyId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
    @GetMapping("/getEmployeeById")
    public ResponseEntity<?>getEmployeeId(@RequestParam Long employeeId){
        try {
            return new ResponseEntity<>(new EntityResponse(employeeService.getEmployeeById(employeeId),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
    @DeleteMapping("/deleteEmployeeById")
    public ResponseEntity<?> deleteEmployeeById(@RequestParam Long employeeId){
        try {
            return new ResponseEntity<>(new EntityResponse(employeeService.deleteEmployeeById(employeeId), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
    @PutMapping("/changeEmployeeStatus")
    public ResponseEntity<?>changeEmployeeStatus(@RequestParam Long employeeId){
        try {
            return new ResponseEntity<>(new EntityResponse(employeeService.changeEmployeeStatus(employeeId), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getAllEmployee",method = RequestMethod.GET)
    private List<Employee>getAllEmployee(){
        return employeeService.getAllEmployee();
    }



}
