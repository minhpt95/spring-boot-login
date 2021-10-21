package com.catdev.project.service.impl;

import com.catdev.project.constant.CommonConstant;
import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.respository.UserRepository;
import com.catdev.project.service.UserService;
import com.catdev.project.util.CommonUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    UserRepository userRepository;


    @Override
    public UserEntity saveToken(String token, UserEntity userEntity) {
        userEntity.setAccessToken(token);
        userEntity.setTokenStatus(true);
        userEntity = userRepository.saveAndFlush(userEntity);

        return userEntity;
    }


    @Override
    public UserEntity clearToken(UserEntity userEntity) {
        userEntity.setAccessToken(null);
        userEntity.setTokenStatus(false);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public void clearAllToken() {
        List<UserEntity> userEntities = userRepository.findAll().stream().peek(x -> {
            x.setTokenStatus(false);
            x.setAccessToken(null);
        }).toList();

        userRepository.saveAll(userEntities);
    }

    @Override
    public UserEntity findUserEntityByEmailForLogin(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);

        if (optionalUserEntity.isEmpty()) {
            throw new ProductException(new ErrorResponse(
                    ErrorConstant.Code.LOGIN_INVALID,
                    ErrorConstant.Type.LOGIN_INVALID,
                    ErrorConstant.MessageEN.LOGIN_INVALID,
                    ErrorConstant.MessageVI.LOGIN_INVALID
            ));
        }
        return optionalUserEntity.get();
    }


    @Override
    public UserEntity findUserEntityByEmail(String email) {
        UserEntity UserEntity = userRepository.findByEmail(email).orElse(null);
        return UserEntity;
    }

    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setCurrentAddress(userEntity.getCurrentAddress());
        userDto.setDescription(userEntity.getDescription());
        userDto.setEmail(userEntity.getEmail());
        userDto.setIdentityCard(userEntity.getIdentityCard());
        userDto.setEnabled(userEntity.isEnabled());
        userDto.setPermanentAddress(userEntity.getPermanentAddress());
        userDto.setPhoneNumber1(userEntity.getPhoneNumber1());
        userDto.setPhoneNumber2(userEntity.getPhoneNumber2());
        return userDto;
    }

    @Override
    public ListResponseDto<UserDto> getUserList(int pageIndex, int pageSize) {
        Page<UserEntity> page;
        page = userRepository.findAll(CommonUtil.buildPageable(pageIndex, pageSize, null, null));
        Page<UserDto> userDtoPage = page.map(this::convertToDto);
        ListResponseDto<UserDto> result = new ListResponseDto<>();
        return result.buildResponseList(userDtoPage, pageSize, pageIndex, userDtoPage.getContent());
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserForm form) {
        List<UserEntity> userEntityList = userRepository.findAllByEmail(form.getEmail());
        if (!userEntityList.isEmpty()) {
            throw new ProductException(
                    new ErrorResponse(
                            ErrorConstant.Code.ALREADY_EXISTS,
                            ErrorConstant.Type.FAILURE,
                            String.format(ErrorConstant.MessageEN.ALREADY_EXISTS,form.getEmail()),
                            String.format(ErrorConstant.MessageEN.ALREADY_EXISTS,form.getEmail())
                    )
            );
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(form.getEmail());
        userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
        userEntity.setEnabled(true);
        userEntity.setIdentityCard(form.getIdentityCard());
        Instant createDate = Instant.now();
        userEntity.setCreatedDate(createDate);
        userEntity = userRepository.save(userEntity);
        return convertToDto(userEntity);
    }

    @Override
    public Boolean confirmEmail(Long id, Instant timeOut) {
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.MessageEN.NOT_EXISTS, "userEntity"),
                    String.format(ErrorConstant.MessageVI.NOT_EXISTS, "userEntity"),
                    ErrorConstant.Type.FAILURE));
        }
        Instant timeSend = userEntity.getCreatedDate();
        Instant timePlus = timeSend.plusMillis(1800000);
        if (timePlus.isBefore(timeOut)) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    ErrorConstant.MessageEN.END_OF_TIME,
                    ErrorConstant.MessageVI.END_OF_TIME,
                    ErrorConstant.Type.FAILURE));
        }
        userEntity.setEnabled(true);
        String pwd = RandomStringUtils.random(12, CommonConstant.characters);
        userEntity.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean forgotPassword(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.MessageEN.NOT_EXISTS, "userEntity"),
                    String.format(ErrorConstant.MessageVI.NOT_EXISTS, "userEntity"),
                    ErrorConstant.Type.FAILURE));
        }
        String pwd = RandomStringUtils.random(12, CommonConstant.characters);
        userEntity.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean changePassword(Long id) {
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.MessageEN.NOT_EXISTS, "userEntity"),
                    String.format(ErrorConstant.MessageVI.NOT_EXISTS, "userEntity"),
                    ErrorConstant.Type.FAILURE));
        }
        String pwd = RandomStringUtils.random(12, CommonConstant.characters);
        userEntity.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean changeStatus(Long id, Boolean status) {
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.MessageEN.NOT_EXISTS, "userEntity"),
                    String.format(ErrorConstant.MessageVI.NOT_EXISTS, "userEntity"),
                    ErrorConstant.Type.FAILURE));
        }
        userEntity.setEnabled(status);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public UserDto updateUser(UpdateUserForm form) {
        UserEntity userEntity = userRepository.findUserEntityById(form.getId());
        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.MessageEN.NOT_EXISTS, "userEntity"),
                    String.format(ErrorConstant.MessageVI.NOT_EXISTS, "userEntity"),
                    ErrorConstant.Type.FAILURE));
        }
        userEntity.setEmail(form.getEmail());
        userEntity.setName(form.getName());
        userEntity.setIdentityCard(form.getIdentityCard());
        userEntity.setPhoneNumber1(form.getPhoneNumber1());
        userEntity.setPhoneNumber2(form.getPhoneNumber2());
        userEntity.setCurrentAddress(form.getCurrentAddress());
        userEntity.setPermanentAddress(form.getPermanentAddress());
        userEntity.setDescription(form.getDescription());

        userEntity = userRepository.save(userEntity);
        return convertToDto(userEntity);
    }
}
