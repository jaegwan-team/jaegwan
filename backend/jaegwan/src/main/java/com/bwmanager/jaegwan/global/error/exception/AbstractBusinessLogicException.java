package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}