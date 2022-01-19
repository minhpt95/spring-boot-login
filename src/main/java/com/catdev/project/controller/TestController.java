package com.catdev.project.controller;

import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ResponseDto;
import com.catdev.project.util.TripleDESUtil;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
import java.util.Arrays;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping(value = "/encrypt")
    public ResponseDto<String> encryptAPI(
            String stringNeedEncrypt
    ) {
        ResponseDto<String> responseDto = new ResponseDto<>();

        try {
            TripleDESUtil tripleDESUtil = new TripleDESUtil("CARD_QUERY_API");
            responseDto.setContent(tripleDESUtil.encrypt(stringNeedEncrypt));
            responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
            responseDto.setMessageVN(ErrorConstant.MessageVI.SUCCESS);
            responseDto.setMessageEN(ErrorConstant.MessageEN.SUCCESS);
            responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseDto;
    }

}
