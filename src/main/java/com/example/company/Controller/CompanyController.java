package com.example.company.Controller;

import com.example.company.Model.Company;
import com.example.company.Model.Response.EntityResponse;
import com.example.company.Model.SaveRequest.SaveCompanyRequest;
import com.example.company.Model.SaveRequest.SaveEmployeeRequest;
import com.example.company.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;
    @Autowired
    @GetMapping("/firstapi")
    public String firstapi(){
        return "its working";
    }
    @PostMapping("/saveOrUpdateCompany")
    public ResponseEntity<?> saveOrUpdateCompany(@RequestBody SaveCompanyRequest saveCompanyRequest)throws Exception{
        return new ResponseEntity<>(companyService.saveOrUpdateCompany(saveCompanyRequest),HttpStatus.OK);
    }

    @GetMapping("/getCompanyById")
    public ResponseEntity<?> getCompanyById(@RequestParam Long companyId)  {
        try {
            return new  ResponseEntity<>(new EntityResponse(companyService.getCompanyById(companyId),0), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
    @GetMapping("/getEmployeeByCompanyId")
    public ResponseEntity<?> getEmployeeByCompanyId(@RequestParam Long companyId,
                                                    @RequestParam (defaultValue = "0",required = false)Integer pageNo,
                                                    @RequestParam(defaultValue = "30",required = false)Integer pageSize,
                                                    @RequestParam(required = false)String employeeName,
                                                    @RequestParam(required = false)String employeeEmail,
                                                    @RequestParam(required = false)String employeeDesignation,
                                                    @RequestParam(required = false)String employeeMobileNo,
                                                    @RequestParam(required = false)String employyeeCode){
        try {
            Pageable pageable = PageRequest.of(pageNo,pageSize);
            return new ResponseEntity<>(new EntityResponse(companyService.getEmployeeByCompanyId(companyId,employeeName,employeeEmail,employeeDesignation,employeeMobileNo,employyeeCode,pageable),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }

    }
    @RequestMapping(value = "/getAllCompanyEmployee",method = RequestMethod.GET)
    private ResponseEntity<?>getAllCompanyEmployee(@RequestParam(defaultValue = "0",required = false)Integer pageNo,
                                                   @RequestParam(defaultValue = "30",required = false)Integer pageSize,
                                                   @RequestParam(required = false)String companyName,
                                                   @RequestParam(required = false)String employeeName,
                                                   @RequestParam(required = false)String employeeEmail,
                                                   @RequestParam(required = false)String employeeDesignation,
                                                   @RequestParam(required = false)String employeeMobileNo,
                                                   @RequestParam(required = false)String employeeCode,
                                                   @RequestParam(required = false)String companyField){
        try {
            Pageable pageable = PageRequest.of(pageNo,pageSize);
            return new ResponseEntity<>(new EntityResponse(companyService.getAllCompanyEmployee(companyName,employeeName,employeeEmail,employeeDesignation,employeeMobileNo,employeeCode,companyField,pageable),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/getAllCompany",method = RequestMethod.GET)
    private ResponseEntity<?>getAllCompany(){
        try {
            return new ResponseEntity<>(new EntityResponse(companyService.getAllCompany(), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteCompanyById")
    public ResponseEntity<?> deleteCompanyById(@RequestParam Long companyId){
        try {
            return new ResponseEntity<>(new EntityResponse(companyService.deleteCompanyById(companyId), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @PutMapping("/changeCompanyStatus")
    public ResponseEntity<?>changeCompanyStatus(@RequestParam Long companyId){
        try {
            return new ResponseEntity<>(new EntityResponse(companyService.changeCompanyStatus(companyId), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }


}
