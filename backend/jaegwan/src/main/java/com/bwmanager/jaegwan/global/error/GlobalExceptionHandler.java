package com.bwmanager.jaegwan.global.error;

import com.bwmanager.jaegwan.global.error.exception.AbstractBusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractBusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(AbstractBusinessLogicException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleAccountException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.BAD_REQUEST_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}