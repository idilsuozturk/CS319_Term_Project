package com.converters;

import java.util.ArrayList;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class IntegerArrayListToJsonConverter implements AttributeConverter<ArrayList<Integer>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ArrayList<Integer> attribute) {
        try {
            if (attribute == null || attribute.isEmpty()) {
                return "[]"; // Store as empty JSON array instead of null
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert ArrayList<Integer> to JSON", e);
        }
    }

    @Override
    public ArrayList<Integer> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData, new TypeReference<ArrayList<Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to ArrayList<Integer>", e);
        }
    }
}

