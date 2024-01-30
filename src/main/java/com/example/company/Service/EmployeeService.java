package com.example.company.Service;

import com.example.company.Model.Employee;
import com.example.company.Model.SaveRequest.SaveEmployeeRequest;

import java.util.List;

public interface EmployeeService {
    Object saveOrUpdateEmployee(SaveEmployeeRequest saveEmployeeRequest, Long companyId) throws Exception;

    Object getEmployeeById(Long employeeId) throws Exception;

    Object deleteEmployeeById(Long employeeId) throws Exception;

    Object changeEmployeeStatus(Long employeeId) throws Exception;

    List<Employee> getAllEmployee();
}
