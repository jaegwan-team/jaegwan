package com.bwmanager.jaegwan.ingredient.entity;

import com.bwmanager.jaegwan.global.converter.CommonType;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import com.bwmanager.jaegwan.ingredient.exception.IngredientServiceException;
import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum Unit implements CommonType {

    G("g", "1"),
    KG("kg", "2"),
    ML("ml", "3"),
    L("L", "4");

    private final String desc;
    private final String code;

    Unit(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }

    public static Unit fromDesc(String desc) {
        return EnumSet.allOf(Unit.class).stream()
                .filter(v -> v.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new IngredientServiceException(ErrorCode.INGREDIENT_UNIT_NOT_FOUND));
    }
}
