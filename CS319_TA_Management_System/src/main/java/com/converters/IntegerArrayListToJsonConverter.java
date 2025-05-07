package com.converters;

import java.util.ArrayList;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class IntegerArrayListToJsonConverter implements AttributeConverter<ArrayList<Integer>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ArrayList<Integer> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ArrayList<Integer> to JSON", e);
        }
    }

    @Override
    public ArrayList<Integer> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<ArrayList<Integer>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to ArrayList<Integer>", e);
        }
    }
}

