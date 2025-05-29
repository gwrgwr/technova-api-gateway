package com.technova.apigateway.exception;

public record RestExceptionDTO(String path, Integer statusCode, String error) {
}
