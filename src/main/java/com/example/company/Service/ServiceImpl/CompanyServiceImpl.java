package com.example.company.Service.ServiceImpl;

import com.example.company.Model.Company;
import com.example.company.Model.SaveRequest.SaveCompanyRequest;
import com.example.company.Repository.CompanyRepository;
import com.example.company.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public String saveOrUpdateCompany(SaveCompanyRequest saveCompanyRequest) {
        if(companyRepository.existsById(saveCompanyRequest.getCompanyId())){
            Company company = companyRepository.findById(saveCompanyRequest.getCompanyId()).get();
            company.setCompanyName(saveCompanyRequest.getCompanyName());
            company.setCompanyWebsite(saveCompanyRequest.getCompanyWebsite());
            company.setCompanyField(saveCompanyRequest.getCompanyFeild());
            company.setCompanyIsDeleted(false);
            company.setCompanyIsActive(true);
            companyRepository.save(company);
            return "Updated sucessfully";
        }else {
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

        if (companyRepository.existsById(companyId)){
            Company company = companyRepository.findById((companyId)).get();
            return company;
        }else {
            throw new  Exception("company not found");
        }


    }

    @Override
    public List<Company> getAllCompany() {
        List<Company> company= companyRepository.getAllCompany();
        return  company;
    }

    @Override
    public String deleteCompanyById(Long companyId) throws Exception {
        if (companyRepository.existsById(companyId)){
            Company company = companyRepository.findById(companyId).get();
            company.setCompanyIsDeleted(false);
            companyRepository.save(company);
            return "deleted sucessfully";
        }else {
            throw new  Exception("company not found");
        }
    }

    @Override
    public String changeCompanyStatus(Long companyId) throws Exception {
        if (companyRepository.existsById(companyId)){
            Company company = companyRepository.findById(companyId).get();
            if (company.getCompanyIsActive()){
                company.setCompanyIsActive(true);
                return " company inactive";
            }else {
                company.setCompanyIsActive(false);
                return "company active";
            }
        }else {
            throw  new Exception("comapny not found");

        }
    }
}
