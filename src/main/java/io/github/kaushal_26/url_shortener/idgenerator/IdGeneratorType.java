package io.github.kaushal_26.url_shortener.idgenerator;

import lombok.Getter;

@Getter
public enum IdGeneratorType {

    UUID("uuid");

    private final String type;

    IdGeneratorType(String type) {
        this.type = type;
    }

    public static IdGeneratorType fromString(String type) {
        for (IdGeneratorType idGeneratorType : IdGeneratorType.values()) {
            if (idGeneratorType.type.equalsIgnoreCase(type)) {
                return idGeneratorType;
            }
        }
        throw new IllegalArgumentException("Unknown ID generator type: " + type);
    }

}
