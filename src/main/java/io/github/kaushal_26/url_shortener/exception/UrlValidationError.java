package io.github.kaushal_26.url_shortener.exception;

public class UrlValidationError extends GenericException {

    public UrlValidationError(String url, String message) {
        super("URL validation failed for: " + url + ", As " + message, ErrorCode.URL_VALIDATIONS_FAILED);
    }

}
