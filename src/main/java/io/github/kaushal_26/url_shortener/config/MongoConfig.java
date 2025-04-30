package io.github.kaushal_26.url_shortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // This class is used to enable MongoDB auditing features
    // such as @CreatedDate and @LastModifiedDate annotations.
    // No additional configuration is needed here.
}
