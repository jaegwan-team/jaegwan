package com.bwmanager.jaegwan.ingredient.entity;

import com.bwmanager.jaegwan.global.converter.AbstractEnumAttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UnitConverter extends AbstractEnumAttributeConverter<Unit> {
    public static final String ENUM_NAME = "재료측정단위";

    public UnitConverter() {
        super(Unit.class, false, ENUM_NAME);
    }
}
