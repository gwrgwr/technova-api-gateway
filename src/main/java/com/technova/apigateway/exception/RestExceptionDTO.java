package com.technova.apigateway.exception;

public record RestExceptionDTO(String path, String message, Integer statusCode, String error) {
}
