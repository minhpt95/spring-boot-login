package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.entity.RefreshTokenEntity;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import com.catdev.project.exception.TokenRefreshException;
import com.catdev.project.jwt.payload.JwtProvider;
import com.catdev.project.jwt.payload.JwtResponse;
import com.catdev.project.jwt.payload.request.TokenRefreshRequest;
import com.catdev.project.jwt.payload.response.TokenRefreshResponse;
import com.catdev.project.readable.form.LoginForm;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.security.service.UserPrinciple;
import com.catdev.project.service.RefreshTokenService;
import com.catdev.project.service.UserService;
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
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

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
                        roles)
        );
        responseDto.setRemainTime(jwtProvider.getRemainTimeFromJwtToken(jwt));

        return responseDto;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromEmail(user.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ProductException(
                    new ErrorResponse());
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        UserEntity userEntity = userService.findUserEntityByEmail(userPrinciple.getEmail());

        userService.clearToken(userEntity);

        new SecurityContextLogoutHandler().logout(request,response,authentication);
    }

    @PostMapping("/forgot")
    public void forgotPassword(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ProductException(
                    new ErrorResponse());
        }
        new SecurityContextLogoutHandler().logout(request,response,authentication);
    }
}
