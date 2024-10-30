package com.bwmanager.jaegwan.global.error.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;

public class ImageException extends AbstractBusinessLogicException {
    public ImageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
