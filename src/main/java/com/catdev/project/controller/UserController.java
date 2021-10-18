package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserService userService;
    @Autowired
    JavaMailSender emailSender;

    @PutMapping(value = "/confirmEmail/{id}")
    public  ResponseDto<Boolean> confirmEmail(@PathVariable Long id , Instant timeOut){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.confirmEmail(id,timeOut));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }
    @PutMapping(value = "/forgotPassword/{email}")
    public  ResponseDto<Boolean> forgotPassword(@PathVariable String email){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.forgotPassword(email));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/changePassword/{id}")
    public  ResponseDto<Boolean> changePassword(@PathVariable Long id){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changePassword(id));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }
    @PutMapping(value = "/changeStatus/{id}")
    public  ResponseDto<Boolean> changeStatus(@PathVariable Long id ,Boolean status){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changeStatus(id,status));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/updateUser")
    public ResponseDto<UserDto> updateUser(
            @RequestBody UpdateUserForm form
    ) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.updateUser(form));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }


}
