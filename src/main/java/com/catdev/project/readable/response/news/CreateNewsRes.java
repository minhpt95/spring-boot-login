package com.catdev.project.readable.response.news;

import com.catdev.project.dto.User.UserDto;
import com.catdev.project.dto.employee.EmployeeAuthorDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CreateNewsRes {
    private Long id;
    private String title;
    private String content;
    private boolean active;
    private EmployeeAuthorDto createdBy;
    private Instant createdDate;
}
