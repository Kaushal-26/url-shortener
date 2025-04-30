package io.github.kaushal_26.url_shortener.repository;

import io.github.kaushal_26.url_shortener.model.Url;

import java.util.Optional;

public interface UrlRepository {

    Optional<Url> find(String shortUrl);

    Url save(Url url);

    Optional<Url> update(String shortUrl, String newOriginalUrl);

    Optional<Url> delete(String shortUrl);

    Url updateAccessedDetails(String shortUrl, long accessCount);

}
