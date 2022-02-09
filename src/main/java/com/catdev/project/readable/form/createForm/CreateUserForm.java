package com.catdev.project.readable.form.createForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserForm {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String identityCard;

    @NotEmpty
    private String phoneNumber1;
    private String phoneNumber2;

    @NotEmpty
    private String currentAddress;

    @NotEmpty
    private String permanentAddress;
    private String description;
}
