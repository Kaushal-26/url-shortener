package io.github.kaushal_26.url_shortener.exception;

import java.io.Serial;

public class NotImplementedError extends GenericException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotImplementedError(String message) {
        super(message, ErrorCode.NOT_IMPLEMENTED);
    }

}
