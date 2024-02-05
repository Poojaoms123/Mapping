package com.example.company.Service;

import com.example.company.Model.Company;
import com.example.company.Model.SaveRequest.SaveCompanyRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyService {
    String saveOrUpdateCompany(SaveCompanyRequest saveCompanyRequest);

    Company getCompanyById(Long companyId) throws Exception;

    List<Company> getAllCompany();

    String deleteCompanyById(Long companyId) throws Exception;

    String changeCompanyStatus(Long companyId) throws Exception;

    Object getEmployeeByCompanyId(Long companyId) throws Exception;


    Object getEmployeeByCompanyId(Long companyId, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode);

    Object getEmployeeByCompanyId(Long companyId, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode, Pageable pageable);

    Object getAllCompanyEmployee();

    Object getAllCompanyEmployee(String companyName, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employeeCode, String companyFeild, Pageable pageable);
}
