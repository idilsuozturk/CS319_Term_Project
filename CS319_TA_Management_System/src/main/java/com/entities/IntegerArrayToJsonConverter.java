package com.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class IntegerArrayToJsonConverter implements AttributeConverter<Integer[], String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Integer[] attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Integer[] to JSON", e);
        }
    }

    @Override
    public Integer[] convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, Integer[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to Integer[]", e);
        }
    }
}
