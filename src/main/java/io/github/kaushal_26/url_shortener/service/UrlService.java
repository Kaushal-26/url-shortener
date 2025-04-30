package io.github.kaushal_26.url_shortener.service;

import io.github.kaushal_26.url_shortener.model.Url;
import io.github.kaushal_26.url_shortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class UrlService {
    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private Url getUrl(String shortUrl) {
        return urlRepository.findByShortUrlAndDeletedAtIsNull(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for shortUrl: " + shortUrl));
    }

    public long getAccessCount(String shortUrl) {
        Url url = this.getUrl(shortUrl);
        return url.getAccessCount();
    }

    public Instant getLastAccessedAt(String shortUrl) {
        Url url = this.getUrl(shortUrl);
        return url.getLastAccessedAt();
    }

    public Url createShortUrl(String originalUrl) {
        // TODO: Implement URL shortening logic
        return urlRepository.save(Url.builder().originalUrl(originalUrl).build());
    }

    public Url updateShortUrl(String shortUrl, String newOriginalUrl) {
        Url url = this.getUrl(shortUrl);
        url.setOriginalUrl(newOriginalUrl);
        url.setAccessCount(0);
        url.setLastAccessedAt(null);
        return urlRepository.save(url);
    }

    public void deleteShortUrl(String shortUrl) {
        Url url = this.getUrl(shortUrl);
        url.setDeletedAt(Instant.now());
        urlRepository.delete(url);
    }
}
