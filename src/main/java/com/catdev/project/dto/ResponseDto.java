package com.catdev.project.dto;

import com.catdev.project.exception.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> extends ErrorResponse {
    private T content;
    private Long remainTime;

    public ResponseDto(int errorCode, String errorType, String messageEN, String messageVN, T content) {
        super(errorCode, errorType, messageEN, messageVN);
        this.content = content;
    }

    public ResponseDto() {
    }
}
