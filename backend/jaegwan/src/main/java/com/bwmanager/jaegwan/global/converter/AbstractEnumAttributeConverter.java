package com.bwmanager.jaegwan.global.converter;

import com.bwmanager.jaegwan.global.error.ErrorCode;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public class AbstractEnumAttributeConverter<E extends Enum<E> & CommonType> implements AttributeConverter<E, String> {

    /**
     * 대상 Enum 클래스의 {@link Class} 객체
     */
    private final Class<E> targetEnumClass;

    /**
     * <code>nullable = false</code> 이면, 변환할 값이 null로 들어왔을 때 예외가 발생한다.
     * <code>nullable = true</code> 이면, 변환할 값이 null일 때, 예외 없이 실행하며,
     * 특히 code로 변환 시에는 빈 문자열("")로 변환한다.
     */
    private final boolean nullable;

    /**
     * <code>nullable = false</code> 일 때 출력할 오류 메시지에서 enum에 대한 설명을 위해 enum의 설명적 이름을 받는다.
     */
    private final String enumName;

    /**
     * 문자열 값에 해당하는 enum이 존재하지 않는 경우 예외에 넣을 errorCode를 받는다.
     */
    private final ErrorCode errorCode;

    public AbstractEnumAttributeConverter(Class<E> targetEnumClass, boolean nullable, ErrorCode errorCode, String enumName) {
        this.targetEnumClass = targetEnumClass;
        this.nullable = nullable;
        this.errorCode = errorCode;
        this.enumName = enumName;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 저장할 수 없습니다.", enumName));
        }
        return EnumValueConvertUtils.toCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        return EnumValueConvertUtils.ofCode(targetEnumClass, errorCode, dbData);
    }
}
