package com.example.company.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "company1_Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "company_field")
    private String companyField;

    @Column(name = "company_is_deleted")
    private Boolean companyIsDeleted;

    @Column(name = "company_is_active")
    private Boolean companyIsActive;

    @CreationTimestamp
    @Column(name = "company_created_at",updatable = false)
    private LocalDateTime companyCreatedAt;

    @UpdateTimestamp
    @Column(name = "company_updated_at")
    private LocalDateTime companyUpdatedAt;


}
