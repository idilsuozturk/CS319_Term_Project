package com.converters;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringArrayToJsonConverter implements AttributeConverter<String[], String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        if (attribute == null) {
            return null;  // Return null if the input is null
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert String[] to JSON", e);
        }
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;  // Return null if the database value is null
        }
        try {
            return mapper.readValue(dbData, String[].class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to String[]", e);
        }
    }
}

