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
     * auth
     */
    TOKEN_EXPIRED(UNAUTHORIZED, "AUTH-000", "토큰이 만료되었습니다."),
    TOKEN_NOT_VALID(UNAUTHORIZED, "AUTH-001", "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(UNAUTHORIZED, "AUTH-002", "토큰이 전달되지 않았습니다."),
    JWT_CREATE_INVALID_INPUT(UNAUTHORIZED, "AUTH-003", "토큰 발급 과정에서 문제가 발생했습니다."),
    KAKAO_AUTHENTICATION_FAILED(UNAUTHORIZED, "AUTH-100", "카카오 인증 과정에서 문제가 발생했습니다."),
    KAKAO_ID_TOKEN_DECODE_FAILED(UNAUTHORIZED, "AUTH-101", "카카오 인증 과정에서 문제가 발생했습니다."),

    /**
     * Member
     */
    MEMBER_NOT_FOUND(NOT_FOUND, "MEMBER-000", "사용자가 존재하지 않습니다."),

    /**
     * Image
     */
    IMAGE_NOT_FOUND(NOT_FOUND, "IMAGE-000", "이미지를 찾을 수 없습니다."),
    IMAGE_UPLOAD_ERROR(CONFLICT, "IMAGE-001", "이미지 업로드 중 에러가 발생했습니다."),

    /**
     * Restaurant
     */
    RESTAURANT_NOT_FOUND(NOT_FOUND, "RESTAURANT-000", "식당이 존재하지 않습니다."),
    RESTAURANT_UNAUTHORIZED(FORBIDDEN, "RESTAURANT-001", "식당에 대한 접근 권한이 없습니다."),
    RESTAURANT_MEMBER_NOT_FOUND(NOT_FOUND, "RESTAURANT-002", "식당에 등록되지 않은 사용자입니다."),
    RESTAURANT_MEMBER_DUPLICATED(BAD_REQUEST, "RESTAURANT-003", "이미 등록된 사용자입니다."),

    /**
     * Receipt
     */
    RECEIPT_NOT_FOUND(NOT_FOUND, "RECEIPT-000", "구매내역이 존재하지 않습니다."),

    /**
     * Ingredient
     */
    INGREDIENT_NOT_FOUND(NOT_FOUND, "INGREDIENT-000", "등록되지 않은 재료입니다."),
    INGREDIENT_CATEGORY_NOT_FOUND(NOT_FOUND, "INGREDIENT-001", "등록되지 않은 재료 카테고리입니다."),
    INGREDIENT_UNIT_NOT_FOUND(NOT_FOUND, "INGREDIENT-002", "재료 단위가 존재하지 않습니다."),

    /**
     * Enum
     */
    ENUM_NOT_FOUND(NOT_FOUND, "ENUM-000", "code에 해당하는 ENUM이 없습니다."),
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
