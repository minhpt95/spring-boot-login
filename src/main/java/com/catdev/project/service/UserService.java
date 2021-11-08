package com.catdev.project.service;



import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;

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

    Boolean confirmEmail(Long id, Instant timeOut);

    Boolean forgotPassword(String email);

    Boolean changePassword(Long id);

    Boolean changeStatus(Long id,Boolean status);

    UserDto updateUser(UpdateUserForm form);
}
