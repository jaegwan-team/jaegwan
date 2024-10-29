package com.bwmanager.jaegwan.ingredient.entity;

import lombok.Getter;

@Getter
public enum Unit {

    G("g"),
    KG("kg"),
    ML("ml"),
    L("L");

    private final String value;

    Unit(String value) {
        this.value = value;
    }
}
