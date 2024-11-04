package com.bwmanager.jaegwan.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /**
     * default
     */
    SYSTEM_ERROR(INTERNAL_SERVER_ERROR, "SYSTEM-000", "서비스에 장애가 발생했습니다."),
    BAD_REQUEST_ERROR(BAD_REQUEST, "SYSTEM-001", "유효하지 않은 요청입니다."),

    /**
     * Member
     */
    MEMBER_FORBIDDEN_ERROR(FORBIDDEN, "MEMBER-000", "사용자의 접근 권한이 없습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER-001", "사용자가 존재하지 않습니다."),

    /**
     * Image
     */
    IMAGE_NOT_FOUND(NOT_FOUND, "IMAGE-000", "이미지를 찾을 수 없습니다."),
    IMAGE_UPLOAD_ERROR(CONFLICT, "IMAGE-001", "이미지 업로드 중 에러가 발생했습니다."),

    /**
     * Restaurant
     */
    RESTAURANT_FORBIDDEN_ERROR(FORBIDDEN, "RESTAURANT-000", "식당에 대한 접근 권한이 없습니다."),
    RESTAURANT_NOT_FOUND(NOT_FOUND, "RESTAURANT-001", "식당이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}