package com.technova.apigateway.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonMapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * Cria um JavaType representando um tipo genérico, ex: Result<UserDTO>.
     *
     * @param outerClass Classe externa (como Result.class)
     * @param innerClass Classe interna (como UserResponseDTO.class)
     * @param <T>        Tipo da classe interna
     * @return JavaType representando o tipo parametrizado
     */
    public static <T> JavaType constructGenericType(Class<?> outerClass, Class<T> innerClass) {
        TypeFactory typeFactory = OBJECT_MAPPER.getTypeFactory();
        return typeFactory.constructParametricType(outerClass, innerClass);
    }
}
