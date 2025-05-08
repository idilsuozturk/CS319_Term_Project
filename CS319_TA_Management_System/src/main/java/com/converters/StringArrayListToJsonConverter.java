package com.converters;

import java.util.ArrayList;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
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
}

