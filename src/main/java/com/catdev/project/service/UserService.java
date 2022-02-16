package com.catdev.project.service;



import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.readable.request.ChangePasswordReq;
import com.catdev.project.readable.request.ChangeStatusAccountReq;

import java.time.Instant;

public interface UserService {
    UserEntity saveToken(String token, UserEntity userEntity);

    UserEntity clearToken(UserEntity userEntity);

    void clearAllToken();

    UserEntity findUserEntityByEmailForLogin(String email);

    UserEntity findUserEntityByEmail(String email);

    ListResponseDto<UserDto> getUserList(int pageIndex,
                                         int pageSize
    );
    UserDto createUser(CreateUserForm form);

    Boolean activateEmail(Long id, Instant timeOut);

    Boolean forgotPassword(String email);

    Boolean changePassword(ChangePasswordReq changePasswordReq);

    Boolean changeStatus(ChangeStatusAccountReq changeStatusAccountReq);

    UserDto updateUser(UpdateUserForm form);
}
