package com.catdev.project.readable.form.createForm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateUserForm {
    private String name;
    private String email;
    private String password;
    private String identityCard;
    private String phoneNumber1;
    private String phoneNumber2;
    private String currentAddress;
    private String permanentAddress;
    private String description;
}
