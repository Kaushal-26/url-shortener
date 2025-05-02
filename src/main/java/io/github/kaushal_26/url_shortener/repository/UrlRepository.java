package io.github.kaushal_26.url_shortener.repository;

import io.github.kaushal_26.url_shortener.model.Url;

import java.util.Optional;

public interface UrlRepository {

    Optional<Url> find(String code);

    Url save(Url url);

    Optional<Url> update(String code, String newOriginalUrl);

    Optional<Url> delete(String code);

    Url updateAccessedDetails(String code, long accessCount);

}
