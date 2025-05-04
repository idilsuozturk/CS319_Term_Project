package com.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class StringArrayToJsonConverter implements AttributeConverter<String[], String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ArrayList<Integer> to JSON", e);
        }
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, String[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to ArrayList<Integer>", e);
        }
    }
}
