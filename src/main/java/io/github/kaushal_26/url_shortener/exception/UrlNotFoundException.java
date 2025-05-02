package io.github.kaushal_26.url_shortener.exception;

import java.io.Serial;

public class UrlNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UrlNotFoundException(String shortUrl) {
        super("ShortUrl", shortUrl);
    }

}
