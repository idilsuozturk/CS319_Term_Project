package com.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringArrayToJsonConverter implements AttributeConverter<String[], String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        try {
            if (attribute == null || attribute.length == 0) {
                return "[]"; // Store empty array
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert String[] to JSON", e);
        }
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.trim().isEmpty()) {
                return new String[0]; // Return empty array
            }
            return objectMapper.readValue(dbData, String[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to String[]", e);
        }
    }
}



