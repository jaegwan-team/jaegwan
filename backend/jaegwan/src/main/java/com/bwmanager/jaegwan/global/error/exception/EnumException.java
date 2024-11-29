package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class EnumException extends AbstractBusinessLogicException {
    public EnumException(ErrorCode errorCode) {
        super(errorCode);
    }
}
