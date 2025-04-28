package io.github.kaushal26.urlshortener.repository;

import io.github.kaushal26.urlshortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortUrlAndDeletedAtIsNull(String shortUrl);
}
