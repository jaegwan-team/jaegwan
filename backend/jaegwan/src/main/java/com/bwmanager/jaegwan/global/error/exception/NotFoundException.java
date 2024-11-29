package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class NotFoundException extends AbstractBusinessLogicException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
