package com.bwmanager.jaegwan.ingredient.entity;

import com.bwmanager.jaegwan.global.converter.CommonType;
import lombok.Getter;

@Getter
public enum Unit implements CommonType {

    G("g", "1"),
    KG("kg", "2"),
    ML("ml", "3"),
    L("L", "4"),
    PCS("개", "5"),
    ;

    private final String desc;
    private final String code;

    Unit(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }
}
