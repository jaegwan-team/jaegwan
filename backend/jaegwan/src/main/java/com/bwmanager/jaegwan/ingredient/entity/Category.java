package com.bwmanager.jaegwan.ingredient.entity;

import com.bwmanager.jaegwan.global.converter.CommonType;
import lombok.Getter;

@Getter
public enum Category implements CommonType {

    Vegetables("채소", "1"),
    Fruits("과일", "2"),
    Meat("고기", "3"),
    Seafood("해산물", "4"),
    Dairy("유제품", "5"),
    Grains("곡물", "6"),
    Spices("향신료", "7"),
    Herbs("허브", "8"),
    Oils("오일", "9"),
    ;

    private final String desc;
    private final String code;

    Category(String desc,
             String code) {
        this.desc = desc;
        this.code = code;
    }
}
