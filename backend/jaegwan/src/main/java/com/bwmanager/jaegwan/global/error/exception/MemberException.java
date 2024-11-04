package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class MemberException extends AbstractBusinessLogicException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }

}
