package com.bwmanager.jaegwan.global.converter;

import com.bwmanager.jaegwan.ingredient.exception.IngredientServiceException;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumSet;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumValueConvertUtils {

    public static <T extends Enum<T> & CommonType> T ofCode(Class<T> enumClass,
                                                            String code) {

        if (StringUtils.isBlank(code)) {
            return null;
        }

        return EnumSet.allOf(enumClass).stream()
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IngredientServiceException(String.format("enum={%s}, code={%s}가 존재하지 않습니다.", enumClass.getName(), code)));
    }

    public static <T extends Enum<T> & CommonType> String toCode(T enumValue) {

        if (enumValue == null) {
            return "";
        }

        return enumValue.getCode();
    }
}
