package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.readable.request.CreateCVReq;
import com.catdev.project.service.CurriculumVitaeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/curriculumVitae")
@Log4j2
public class CurriculumVitaeController {
    private CurriculumVitaeService curriculumVitaeService;

    @PostMapping(value = "/joinTheTeam",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseDto<?> joinTheTeam(@ModelAttribute CreateCVReq createCVReq){

        curriculumVitaeService.createCV(createCVReq);

        var responseDto = new ResponseDto();
        responseDto.setErrorCode(ErrorConstant.Type.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        return responseDto;
    }

}
