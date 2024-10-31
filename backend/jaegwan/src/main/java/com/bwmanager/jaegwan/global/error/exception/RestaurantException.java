package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class RestaurantException extends AbstractBusinessLogicException {

    public RestaurantException(ErrorCode errorCode) {
        super(errorCode);
    }

}
