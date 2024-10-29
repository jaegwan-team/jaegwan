package com.bwmanager.jaegwan.global.converter;


import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public class AbstractEnumAttributeConverter<E extends Enum<E> & CommonType> implements AttributeConverter<E, String> {

    /**
     * 대상 Enum 클래스의 {@link Class} 객체
     */
    private Class<E> targetEnumClass;

    /**
     * <code>nullable = false</code> 이면, 변환할 값이 null로 들어왔을 때 예외가 발생한다.
     * <code>nullable = true</code> 이면, 변환할 값이 null일 때, 예외 없이 실행하며,
     * 특히 code로 변환 시에는 빈 문자열("")로 변환한다.
     */
    private boolean nullable;

    /**
     * <code>nullalbe = false</code> 일 때 출력할 오류 메시지에서 enum에 대한 설명을 위해 Enum 의 설명적 이름을 받는다.
     */
    private String enumName;

    public AbstractEnumAttributeConverter(Class<E> targetEnumClass, boolean nullable, String enumName) {
        this.targetEnumClass = targetEnumClass;
        this.nullable = nullable;
        this.enumName = enumName;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        if (!nullable && attribute == null) {
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 저장할 수 없습니다.", enumName));
        }
        return EnumValueConvertUtils.toCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (!nullable && StringUtils.isBlank(dbData)) {
            throw new IllegalArgumentException(String.format("%s(이)가 DB에 NULL 혹은 Empty로(%s) 저장되어 있습니다.",
                    enumName, dbData));
        }
        return EnumValueConvertUtils.ofCode(targetEnumClass, dbData);
    }
}
