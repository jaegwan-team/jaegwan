package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class AuthException extends AbstractBusinessLogicException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

}
