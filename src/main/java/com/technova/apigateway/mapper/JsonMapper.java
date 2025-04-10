package com.technova.apigateway.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> JavaType constructGenericType(Class<?> outerClass, Class<T> innerClass) {
        TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
        return typeFactory.constructParametricType(outerClass, innerClass);
    }
}
