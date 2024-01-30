package com.example.company.Repository;

import com.example.company.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query(value = "select * from company1_Company where company_is_deleted=false ",nativeQuery = true)
    List<Company> getAllCompany();


    Company findByCompanyIdAndCompanyIsDeleted(Long companyId, boolean b);

    boolean existsByCompanyIdAndCompanyIsDeleted(Long companyId, boolean b);
}
