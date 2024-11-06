package com.bwmanager.jaegwan.ingredient.entity;

import com.bwmanager.jaegwan.global.converter.AbstractEnumAttributeConverter;
import com.bwmanager.jaegwan.global.error.ErrorCode;
import jakarta.persistence.Converter;

@Converter
public class CategoryConverter extends AbstractEnumAttributeConverter<Category> {
    public static final String ENUM_NAME = "재료종류";

    public CategoryConverter() {
        super(Category.class, false, ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND, ENUM_NAME);
    }
}
