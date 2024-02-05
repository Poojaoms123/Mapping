package com.example.company.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDTO {
    private Object Data;
    private Long totalElement;
    private Integer pageNumber;
    private Integer totalPage;
}
