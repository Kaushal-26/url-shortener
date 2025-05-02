package io.github.kaushal_26.url_shortener.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ResourceNotFoundException extends GenericException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String resource;

    public ResourceNotFoundException(String resource, String value) {
        super(resource + " not found for value: " + value, ErrorCode.RESOURCE_NOT_FOUND);
        this.resource = resource;
    }

}
