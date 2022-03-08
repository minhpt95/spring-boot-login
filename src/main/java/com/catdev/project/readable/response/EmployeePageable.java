package com.catdev.project.readable.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePageable {
    private String name;
    private String email;
    private String imageUrl;
    private String position;
    private String linkedinUrl;
    private String facebookUrl;
}
