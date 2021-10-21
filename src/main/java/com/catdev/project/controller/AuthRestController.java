package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.entity.RefreshTokenEntity;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import com.catdev.project.exception.TokenRefreshException;
import com.catdev.project.jwt.JwtProvider;
import com.catdev.project.jwt.JwtResponse;
import com.catdev.project.jwt.payload.request.TokenRefreshRequest;
import com.catdev.project.jwt.payload.response.TokenRefreshResponse;
import com.catdev.project.readable.form.LoginForm;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.security.service.UserPrinciple;
import com.catdev.project.service.MailService;
import com.catdev.project.service.RefreshTokenService;
import com.catdev.project.service.UserService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUserForm createUserForm) {
        userService.createUser(createUserForm);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseDto<?> authenticateUser(@RequestBody LoginForm loginForm) {

        ResponseDto<JwtResponse> responseDto = new ResponseDto<>();

        UserEntity user = userService.findUserEntityByEmailForLogin(loginForm.getEmail());

        if(!encoder.matches(loginForm.getPassword(),user.getPassword())){
           throw new ProductException(new ErrorResponse(
                   ErrorConstant.Code.LOGIN_INVALID,
                   ErrorConstant.Type.LOGIN_INVALID,
                   ErrorConstant.MessageEN.LOGIN_INVALID,
                   ErrorConstant.MessageVI.LOGIN_INVALID
           ));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getEmail(),
                        loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        String jwt = jwtProvider.generateJwtToken(authentication);

        user.setAccessToken(jwt);
        user.setTokenStatus(true);

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roles = userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
        responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
        responseDto.setContent(
                new JwtResponse(
                        jwt,
                        refreshToken.getToken(),
                        userPrinciple.getId(),
                        userPrinciple.getUsername(),
                        userPrinciple.getEmail(),
                        roles
                )
        );
        responseDto.setRemainTime(jwtProvider.getRemainTimeFromJwtToken(jwt));

        return responseDto;
    }

    @PostMapping("/refreshToken")
    public ResponseDto<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromEmail(user.getEmail());
                    userService.saveToken(token,user);
                    Long timeRemain = jwtProvider.getRemainTimeFromJwtToken(token);
                    ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
                    responseDto.setRemainTime(timeRemain);
                    responseDto.setMessageVN("Thanh Cong");
                    responseDto.setErrorCode(200);
                    responseDto.setContent(new TokenRefreshResponse(token, requestRefreshToken));
                    return responseDto;
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ProductException(
                    new ErrorResponse());
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        UserEntity userEntity = userService.findUserEntityByEmail(userPrinciple.getEmail());

        userService.clearToken(userEntity);

        new SecurityContextLogoutHandler().logout(request,response,authentication);

        ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
        responseDto.setRemainTime(0L);
        responseDto.setMessageVN("Thanh Cong");
        responseDto.setErrorCode(200);
        return responseDto;

    }

    @SneakyThrows
    @PostMapping("/forgot")
    public ResponseDto<?> forgotPassword(@RequestParam(name = "email",defaultValue = "") String email) {
        String newPasswordGenerate = "Mercon@2021";

        if (StringUtils.isBlank(email)) {
            throw new ProductException(
                    new ErrorResponse()
            );
        }

        UserEntity userEntity = userService.findUserEntityByEmail(email);
        if(userEntity == null){
            throw new ProductException(
                    new ErrorResponse()
            );
        }

        userEntity.setPassword(encoder.encode(newPasswordGenerate));

        mailService.sendEmail(userEntity.getEmail(),"Forgot Password","New password is : " + newPasswordGenerate);

        ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
        responseDto.setRemainTime(0L);
        responseDto.setMessageVN("Thanh Cong");
        responseDto.setErrorCode(200);
        return responseDto;
    }
}
