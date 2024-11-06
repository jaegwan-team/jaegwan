package com.bwmanager.jaegwan.ingredient.exception;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.global.error.exception.AbstractBusinessLogicException;

public class IngredientServiceException extends AbstractBusinessLogicException {
    public IngredientServiceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
