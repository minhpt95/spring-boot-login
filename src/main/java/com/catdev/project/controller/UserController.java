package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.entity.RefreshTokenEntity;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import com.catdev.project.exception.TokenRefreshException;
import com.catdev.project.jwt.JwtProvider;
import com.catdev.project.jwt.payload.request.TokenRefreshRequest;
import com.catdev.project.jwt.payload.response.TokenRefreshResponse;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.readable.request.ChangePasswordReq;
import com.catdev.project.readable.request.ChangeStatusAccountReq;
import com.catdev.project.security.service.UserPrinciple;
import com.catdev.project.service.RefreshTokenService;
import com.catdev.project.service.UserService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    final
    PasswordEncoder encoder;

    final
    UserService userService;

    final
    JavaMailSender emailSender;

    final
    JwtProvider jwtProvider;

    final
    RefreshTokenService refreshTokenService;

    @PutMapping(value = "/activateEmail/{id}")
    public  ResponseDto<Boolean> activateEmail(@PathVariable Long id){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();

        Instant instant = Instant.now();

        responseDto.setContent(userService.activateEmail(id,instant));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/changePassword")
    public  ResponseDto<Boolean> changePassword(@Valid @RequestBody ChangePasswordReq changePasswordReq){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changePassword(changePasswordReq));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/changeStatus")
    public  ResponseDto<Boolean> changeStatus(@Valid @RequestBody ChangeStatusAccountReq changeStatusAccountReq){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changeStatus(changeStatusAccountReq));
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
