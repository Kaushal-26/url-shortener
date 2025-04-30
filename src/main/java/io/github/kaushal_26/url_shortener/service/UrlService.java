package io.github.kaushal_26.url_shortener.service;

import io.github.kaushal_26.url_shortener.model.Url;
import io.github.kaushal_26.url_shortener.repository.UrlRepository;
import io.github.kaushal_26.url_shortener.response.GetAccessCountResponse;
import io.github.kaushal_26.url_shortener.response.GetLastAccessedResponse;
import io.github.kaushal_26.url_shortener.response.ShortUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GetAccessCountResponse getAccessCount(String shortUrl) {
        Url url = this.getUrl(shortUrl);
        log.info("Access count for short URL {}: {}", shortUrl, url.getAccessCount());
        return GetAccessCountResponse.builder().accessCount(url.getAccessCount()).build();
    }

    public GetLastAccessedResponse getLastAccessedAt(String shortUrl) {
        Url url = this.getUrl(shortUrl);
        log.info("Last accessed time for short URL {}: {}", shortUrl, url.getLastAccessedAt());
        return GetLastAccessedResponse.builder().lastAccessedAt(url.getLastAccessedAt()).build();
    }

    public ShortUrlResponse createShortUrl(String originalUrl) {
        // TODO: Implement URL shortening logic
        Url url = urlRepository.save(Url.builder().originalUrl(originalUrl).build());
        log.info("Created short URL: {} for original URL: {}", url.getShortUrl(), originalUrl);
        return ShortUrlResponse.builder().shortUrl(url.getShortUrl()).build();
    }

    public ShortUrlResponse updateShortUrl(String shortUrl, String newOriginalUrl) {
        Url url = urlRepository.update(shortUrl, newOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for shortUrl: " + shortUrl));
        log.info("Updated short URL: {} to new original URL: {}", shortUrl, newOriginalUrl);
        return ShortUrlResponse.builder().shortUrl(url.getShortUrl()).build();
    }

    public void deleteShortUrl(String shortUrl) {
        urlRepository.delete(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for shortUrl: " + shortUrl));
        log.info("Deleted short URL: {}", shortUrl);
    }

}
