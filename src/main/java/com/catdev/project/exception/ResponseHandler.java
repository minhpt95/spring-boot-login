package com.catdev.project.exception;

import com.catdev.project.constant.ErrorConstant;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ResponseHandler {
    @ExceptionHandler(ProductException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleProductException(ProductException ex, WebRequest request) {
        return ex.getErrorResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(403);
        errorResponse.setMessageVN("Bạn không đủ quyền để truy cập vào đây");
        errorResponse.setMessageEN("");
        return errorResponse;
    }


    @ExceptionHandler(value = DisabledException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleDisableException(DisabledException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorConstant.Code.USER_INACTIVE);
        errorResponse.setErrorType(ErrorConstant.Type.USER_INACTIVE);
        errorResponse.setMessageEN(ErrorConstant.MessageEN.USER_INACTIVE);
        errorResponse.setMessageVN(ErrorConstant.MessageVI.USER_INACTIVE);
        return errorResponse;
    }

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setMessageEN(ex.getMessage());
        return errorResponse;
    }
}
