package com.example.company.Repository;

import com.example.company.Model.Company;
import com.example.company.Model.Employee;
import com.example.company.Repository.projections.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query(value = "select * from company1_Company where company_is_deleted=false ",nativeQuery = true)
    List<Company> getAllCompany();


    boolean existsByCompanyIdAndCompanyIsDeleted(Long companyId, boolean b);

    Company findByCompanyIdAndCompanyIsDeleted(Long companyId, boolean b);

    boolean existsByCompanyId(Long companyId);


    @Query(value = "select e from Employee as e inner join Company as c on e.company.companyId=c.companyId where e.company.companyId=c.companyId")
    Page<Employee> getByCompanyId(Long companyId,Pageable pageable);


  /*  @Query(value = "select e from Employee as e  inner join  Company as c on  e.companyId.companyId=c.companyId where (:employeeName is null or lower(e.employeeName)like %:employeeName%)and(:employeeEmail is null or lower(e.employeeEmail)like %:employeeEmail%)and(:employeeDesignation is null or lower(e.employeeDesignation)like %:employeeDesignation%)and(:employeeMobileNo is null or lower(e.employeeMobileNo)like %:employeeMobileNo)and (:employyeeCode is null or lower(e.employeeCode)like %:employyeeCode)and e.employeeIsDeleted=false ")
    Page<Company> getEmployeeByCompanyId(String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode, Pageable pageable);*/


    @Query(value = "select e from Employee as e  inner join  Company as c on  e.company.companyId=c.companyId where e.company.companyId = :companyId and ( :employeeName is null or lower(e.employeeName)like %:employeeName%)and(:employeeEmail is null or lower(e.employeeEmail)like %:employeeEmail%)and(:employeeDesignation is null or lower(e.employeeDesignation)like %:employeeDesignation%)and(:employeeMobileNo is null or lower(e.employeeMobileNo)like %:employeeMobileNo%)and (:employyeeCode is null or lower(e.employeeCode)like %:employyeeCode%)and e.employeeIsDeleted=false ")
    Page<Employee> getEmployeeByCompanyId(Long companyId, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employyeeCode, Pageable pageable);

    @Query(value = "select e.employeeId as employeeId,e.employeeName as employeeName,e.employeeDesignation as employeeDesignation,e.employeeMobileNo as employeeMobileNo,e.employeeCode as employeeCode,e.employeeCreatedAt as createdAt,e.employeeIsActive as employeeIsActive,e.company.companyId as companyId,e.company.companyName as companyName,e.company.companyWebsite as companyWebsite,e.company.companyField as companyField,e.employeeEmail as employeeEmail from Employee as e where (:companyName is null or lower(e.company.companyName) like %:companyName%) and (:employeeName is null or lower(e.employeeName) like %:employeeName%) and (:employeeEmail is null or lower(e.employeeEmail) like %:employeeEmail%) and (:employeeDesignation is null or lower(e.employeeDesignation) like %:employeeDesignation%) and (:employeeMobileNo is null or lower(e.employeeMobileNo) like %:employeeMobileNo%) and (:employeeCode is null or lower(e.employeeCode) like %:employeeCode%) and (:companyField is null or lower(e.company.companyField) like %:companyField%) and e.employeeIsDeleted=false and e.company.companyIsDeleted=false order by e.employeeCreatedAt desc ")
    Page<EmployeeProjection> getAllCompanyEmployee(String companyName, String employeeName, String employeeEmail, String employeeDesignation, String employeeMobileNo, String employeeCode, String companyField, Pageable pageable);

    @Query(value = "select e.employeeId as employeeId,e.employeeName as employeeName,e.employeeDesignation as employeeDesignation,e.employeeMobileNo as employeeMobileNo,e.employeeCode as employeeCode,e.employeeCreatedAt as createdAt,e.employeeIsActive as employeeIsActive,e.company.companyId as companyId,e.company.companyName as companyName,e.company.companyWebsite as companyWebsite,e.company.companyField as companyField,e.employeeEmail as employeeEmail from Employee as e where e.company.companyIsDeleted=false and e.employeeIsDeleted=false order by e.employeeCreatedAt desc",nativeQuery = false)
    Page<EmployeeProjection> getAllCompanyEmployee(Pageable pageable);
}
