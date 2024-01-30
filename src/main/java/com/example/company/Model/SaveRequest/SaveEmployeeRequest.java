package com.example.company.Model.SaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveEmployeeRequest {
    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeDesignation;
    private String  employeeMobileNo;
    private String employeeCode;
}
