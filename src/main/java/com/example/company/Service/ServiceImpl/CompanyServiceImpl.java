package com.example.company.Service.ServiceImpl;

import com.example.company.Model.Company;
import com.example.company.Model.Employee;
import com.example.company.Model.Response.PageDTO;
import com.example.company.Model.SaveRequest.SaveCompanyRequest;
import com.example.company.Repository.CompanyRepository;
import com.example.company.Repository.projections.EmployeeProjection;
import com.example.company.Service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public String saveOrUpdateCompany(SaveCompanyRequest saveCompanyRequest) {
        if (companyRepository.existsById(saveCompanyRequest.getCompanyId())) {
            Company company = companyRepository.findById(saveCompanyRequest.getCompanyId()).get();
            company.setCompanyName(saveCompanyRequest.getCompanyName());
            company.setCompanyWebsite(saveCompanyRequest.getCompanyWebsite());
            company.setCompanyField(saveCompanyRequest.getCompanyFeild());
            company.setCompanyIsDeleted(false);
            company.setCompanyIsActive(true);
            companyRepository.save(company);
            return "Updated sucessfully";
        } else {
            Company company = new Company();
            company.setCompanyName(saveCompanyRequest.getCompanyName());
            company.setCompanyWebsite(saveCompanyRequest.getCompanyWebsite());
            company.setCompanyField(saveCompanyRequest.getCompanyFeild());
            company.setCompanyIsDeleted(false);
            company.setCompanyIsActive(true);
            companyRepository.save(company);
            return "save sucessfully";

        }
    }

    @Override
    public Company getCompanyById(Long companyId) throws Exception {

        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findById((companyId)).get();
            return company;
        } else {
            throw new Exception("company not found");
        }


    }

    @Override
    public List<Company> getAllCompany() {
        List<Company> company = companyRepository.getAllCompany();
        return company;
    }

    @Override
    public String deleteCompanyById(Long companyId) throws Exception {
        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findById(companyId).get();
            company.setCompanyIsDeleted(false);
            companyRepository.save(company);
            return "deleted sucessfully";
        } else {
            throw new Exception("company not found");
        }
    }

    @Override
    public String changeCompanyStatus(Long companyId) throws Exception {
        if (companyRepository.existsById(companyId)) {
            Company company = companyRepository.findById(companyId).get();
            if (company.getCompanyIsActive()) {
                company.setCompanyIsActive(true);
                return " company inactive";
            } else {
                company.setCompanyIsActive(false);
                return "company active";
            }
        } else {
            throw new Exception("comapny not found");

        }
    }

    @Override
    public Object getEmployeeByCompanyId(Long companyId) throws Exception {
        if (companyRepository.existsByCompanyId(companyId)) {
           // List<Employee> company = companyRepository.getByCompanyId(companyId);
            return "company";
        } else {
            throw new Exception("company not found");
        }
    }

    @Override
    public Object getEmployeeByCompanyId(Long companyId, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode) {
        return null;
    }

    @Override
    public PageDTO getEmployeeByCompanyId(Long companyId, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode, Pageable pageable) {
        Page<Employee> company = null;
        if (StringUtils.isNotBlank(employeeName) || StringUtils.isNotBlank(employeeEmail) || StringUtils.isNotBlank(employeeDesignation) || StringUtils.isNotBlank(employeeMobileNo) || StringUtils.isNotBlank(employyeeCode)||companyId!=null) {
            if (StringUtils.isNotBlank(employeeName)) {
                employeeName = employeeName.toLowerCase();
            } else {
                employeeName = null;
            }
            if (StringUtils.isNotBlank(employeeEmail)) {
                employeeEmail = employeeEmail.toLowerCase();
            } else {
                employeeEmail = null;
            }
            if (StringUtils.isNotBlank(employeeDesignation)) {
                employeeDesignation = employeeDesignation.toLowerCase();
            } else {
                employeeDesignation = null;
            }
            if (StringUtils.isNotBlank(employyeeCode)) {
                employyeeCode = employyeeCode.toLowerCase();
            } else {
                employyeeCode = null;

            }
            if (StringUtils.isNotBlank(employeeMobileNo)) {
                employeeMobileNo = null;
            }
                company = companyRepository.getEmployeeByCompanyId(companyId,employeeName, employeeEmail, employeeDesignation, employeeMobileNo, employyeeCode, pageable);
            } else {
                company = companyRepository.getByCompanyId(companyId,pageable);
            }

        return new PageDTO(company.getContent(), company.getTotalElements(), company.getNumber(), company.getTotalPages());
        }

    @Override
    public Object getAllCompanyEmployee() {
        return null;
    }

    @Override
    public Object getAllCompanyEmployee(String companyName, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employeeCode, String companyField, Pageable pageable) {
        Page<EmployeeProjection> company=null;
        if (StringUtils.isNotBlank(companyName) || StringUtils.isNotBlank(employeeName) || StringUtils.isNotBlank(employeeEmail) || StringUtils.isNotBlank(employeeDesignation) || StringUtils.isNotBlank(employeeMobileNo) || StringUtils.isNotBlank(employeeCode) || StringUtils.isNotBlank(companyField)) {
            if (StringUtils.isNotBlank(companyName)){
                companyName = companyName.toLowerCase();
            }else {
                companyName =null;
            }
            if (StringUtils.isNotBlank(employeeName)) {
                employeeName = employeeName.toLowerCase();
            } else {
                employeeName = null;
            }
            if (StringUtils.isNotBlank(employeeEmail)) {
                employeeEmail = employeeEmail.toLowerCase();
            } else {
                employeeEmail = null;
            }
            if (StringUtils.isNotBlank(employeeDesignation)) {
                employeeDesignation = employeeDesignation.toLowerCase();
            } else {
                employeeDesignation = null;
            }
            if (StringUtils.isNotBlank(employeeCode)){
                employeeCode = employeeCode.toLowerCase();
            }else {
                employeeCode = null;
            }
            if (StringUtils.isNotBlank(companyField)){
                companyField = companyField.toLowerCase();
            }else {
                companyField = null;
            }
            if (StringUtils.isNotBlank(employeeMobileNo)) {
                employeeMobileNo = null;
            }
            company = companyRepository.getAllCompanyEmployee(companyName, employeeName, employeeEmail, employeeDesignation, employeeMobileNo, employeeCode, companyField, pageable);
        }else {
            company = companyRepository.getAllCompanyEmployee(pageable);
        }
        return new PageDTO(company.getContent(), company.getTotalElements(), company.getNumber(), company.getTotalPages());
    }

}


