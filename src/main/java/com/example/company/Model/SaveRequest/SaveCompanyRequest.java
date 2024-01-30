package com.example.company.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCompanyRequest {
    private Long companyId;
    private String companyName;
    private String companyWebsite;
    private String companyFeild;
}
