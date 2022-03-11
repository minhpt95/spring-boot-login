package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.readable.request.CreateCVReq;
import com.catdev.project.service.CurriculumVitaeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@Log4j2
public class CustomerController {
    private CurriculumVitaeService curriculumVitaeService;

    @PostMapping(value = "/hireDeveloper",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseDto<?> hireDeveloper(@ModelAttribute CreateCVReq createCVReq){

        curriculumVitaeService.createCV(createCVReq);

        var responseDto = new ResponseDto();
        responseDto.setErrorCode(ErrorConstant.Type.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        return responseDto;
    }
}
