package io.github.kaushal_26.url_shortener.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    URL_VALIDATIONS_FAILED(HttpStatus.BAD_REQUEST, "URL validation failed");

    private final HttpStatus statusCode;
    private final String message;

    ErrorCode(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
