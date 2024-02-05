package com.example.company.Repository.projections;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

public interface EmployeeProjection {
    Long getEmployeeId();
    String getEmployeeName();
    String getEmployeeEmail();
    String getEmployeeDesignation();
    String getEmployeeMobileNo();
    String getEmployeeCode();
    LocalDateTime getCreatedAt();
    String getEmployeeIsActive();
    String getCompanyId();
    String getCompanyName();
    String getCompanyWebsite();
    String getCompanyField();
}
