package com.catdev.project.controller;

import com.catdev.project.dto.ResponseDto;
import com.catdev.project.dto.UserDto;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.readable.request.ChangePasswordReq;
import com.catdev.project.readable.request.ChangeStatusAccountReq;
import com.catdev.project.readable.request.CreateCVReq;
import com.catdev.project.service.CurriculumVitaeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@Log4j2
public class CustomerController {

    private CurriculumVitaeService curriculumVitaeService;

    @PostMapping(value = "/joinTheTeam",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseDto<?> joinTheTeam(@ModelAttribute CreateCVReq createCVReq){

        curriculumVitaeService.createCV(createCVReq);

        return null;
    }

    @PutMapping(value = "/changePassword")
    public  ResponseDto<Boolean> changePassword(@Valid @RequestBody ChangePasswordReq changePasswordReq){
        return null;
    }

    @PutMapping(value = "/changeStatus")
    public  ResponseDto<Boolean> changeStatus(@Valid @RequestBody ChangeStatusAccountReq changeStatusAccountReq){
        return null;
    }

    @PutMapping(value = "/updateUser")
    public ResponseDto<UserDto> updateUser(
            @RequestBody UpdateUserForm form
    ) {
        return null;
    }
}
