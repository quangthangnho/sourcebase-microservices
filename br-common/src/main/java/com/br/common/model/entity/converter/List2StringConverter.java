package com.br.common.model.entity.converter;

import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class List2StringConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return CollectionUtils.isEmpty(strings) ? null : String.join(",", strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return StringUtils.isEmpty(s) ? new ArrayList<>() : Arrays.asList(s.split(","));
    }
}
