package com.catdev.project.readable.form.updateForm;

import com.catdev.project.readable.form.createForm.CreateUserForm;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateUserForm extends CreateUserForm {
    private Long id;
}
