package com.bwmanager.jaegwan.ingredient.entity;

import lombok.Getter;

@Getter
public enum Category {

    Vegetables("채소"),
    Fruits("과일"),
    Meat("고기"),
    Seafood("해산물"),
    Dairy("유제품"),
    Grains("곡물"),
    Spices("향신료"),
    Herbs("허브"),
    Oils("오일");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
