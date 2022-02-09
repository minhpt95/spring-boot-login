package com.catdev.project.readable.request;

import javax.validation.constraints.NotEmpty;

public class ForgotPasswordReq {
    @NotEmpty
    private String email;
}
