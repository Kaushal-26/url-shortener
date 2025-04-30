package io.github.kaushal_26.url_shortener.repository;

import io.github.kaushal_26.url_shortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortUrlAndDeletedAtIsNull(String shortUrl);
}
