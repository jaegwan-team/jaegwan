package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class FileException extends AbstractBusinessLogicException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
