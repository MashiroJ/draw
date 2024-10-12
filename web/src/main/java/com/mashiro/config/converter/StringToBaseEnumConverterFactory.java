package com.mashiro.config.converter;

import com.mashiro.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                for (T enumConstant : targetType.getEnumConstants()) {
                    try {
                        if (enumConstant.getName().equalsIgnoreCase(source)) {
                            return enumConstant;
                        }
                        if (enumConstant.getCode().equals(Integer.valueOf(source))) {
                            return enumConstant;
                        }
                    } catch (NumberFormatException e) {
                        // 忽略数字格式异常
                    }
                }
                throw new IllegalArgumentException("非法的枚举值:" + source);
            }
        };
    }
}