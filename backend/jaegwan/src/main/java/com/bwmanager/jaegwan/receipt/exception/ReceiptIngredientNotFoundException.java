package com.bwmanager.jaegwan.receipt.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AbstractBusinessLogicException;

public class ReceiptIngredientNotFoundException extends AbstractBusinessLogicException {
    public ReceiptIngredientNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
