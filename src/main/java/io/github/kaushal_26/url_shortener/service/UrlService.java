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
        return urlRepository.find(shortUrl)
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
        return urlRepository.update(shortUrl, newOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for shortUrl: " + shortUrl));
    }

    public void deleteShortUrl(String shortUrl) {
        urlRepository.delete(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for shortUrl: " + shortUrl));
    }

}
