package io.github.kaushal_26.url_shortener.config;

import io.github.kaushal_26.url_shortener.idgenerator.IdGenerator;
import io.github.kaushal_26.url_shortener.idgenerator.IdGeneratorType;
import io.github.kaushal_26.url_shortener.idgenerator.impl.UUIDImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class IdGeneratorConfig {

    private final IdGeneratorType idGeneratorType;

    public IdGeneratorConfig(@Value("${url.shortener.id-generator.type}") String idGeneratorType) {
        this.idGeneratorType = IdGeneratorType.fromString(idGeneratorType);
    }

    @Bean
    @Primary
    public IdGenerator idGenerator() {
        return switch (idGeneratorType) {
            case UUID -> new UUIDImpl();
        };
    }

}
