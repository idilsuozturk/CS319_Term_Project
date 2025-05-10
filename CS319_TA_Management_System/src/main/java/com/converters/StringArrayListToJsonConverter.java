package com.converters;

import java.util.ArrayList;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/*@Converter
public class StringArrayListToJsonConverter implements AttributeConverter<ArrayList<String>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ArrayList<String> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ArrayList<Integer> to JSON", e);
        }
    }

    @Override
    public ArrayList<String> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<ArrayList<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to ArrayList<Integer>", e);
        }
    }
}*/

@Converter
public class StringArrayListToJsonConverter implements AttributeConverter<ArrayList<Integer>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ArrayList<Integer> attribute) {
        try {
            return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert ArrayList to JSON", e);
        }
    }

    @Override
    public ArrayList<Integer> convertToEntityAttribute(String dbData) {
        try {
            // Handle null or empty string from database
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData, new TypeReference<ArrayList<Integer>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to ArrayList<Integer>", e);
        }
    }
}

