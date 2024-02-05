package com.example.company.Repository;

import com.example.company.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query(value = "select * from company1_Employee where employee_is_deleted=false ",nativeQuery = true)
    List<Employee> getAllEmployee();


}
