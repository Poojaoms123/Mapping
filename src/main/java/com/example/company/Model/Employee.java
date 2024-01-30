package com.example.company.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "company1_Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "employee_designation")
    private String employeeDesignation;

    @Column(name = "employee_Mobile_No")
    private String employeeMobileNo;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "employee_is_deleted")
    private Boolean employeeIsDeleted;

    @Column(name = "employee_is_active")
    private Boolean employeeIsActive;

    @CreationTimestamp
    @Column(name = "employee_created_at",updatable = false)
    private LocalDateTime employeeCreatedAt;

    @UpdateTimestamp
    @Column(name = "employee_updated_at")
    private LocalDateTime employeeUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;

}
