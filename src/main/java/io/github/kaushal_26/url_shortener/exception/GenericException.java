package io.github.kaushal_26.url_shortener.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class GenericException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final ErrorCode errorCode;

    public GenericException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
