package io.github.kaushal_26.url_shortener.service;

import io.github.kaushal_26.url_shortener.config.UrlConfig;
import io.github.kaushal_26.url_shortener.exception.UrlNotFoundException;
import io.github.kaushal_26.url_shortener.exception.UrlValidationError;
import io.github.kaushal_26.url_shortener.idgenerator.IdGenerator;
import io.github.kaushal_26.url_shortener.model.Url;
import io.github.kaushal_26.url_shortener.repository.UrlRepository;
import io.github.kaushal_26.url_shortener.response.*;
import io.github.kaushal_26.url_shortener.util.CommonUtil;
import io.github.kaushal_26.url_shortener.util.EncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlService {

    private final UrlRepository urlRepository;

    private final UrlConfig urlConfig;

    private final IdGenerator idGenerator;

    @Autowired
    public UrlService(UrlRepository urlRepository, UrlConfig urlConfig, IdGenerator idGenerator) {
        this.urlRepository = urlRepository;
        this.urlConfig = urlConfig;
        this.idGenerator = idGenerator;
    }

    private String getCode(String shortUrl) {
        shortUrl = CommonUtil.validateAndNormalizeUrl(shortUrl);
        // Check if the short URL starts with the url shortener base
        if (!shortUrl.startsWith(urlConfig.getBaseUrl())) {
            throw new UrlValidationError(shortUrl, "Short URL does not match the base URL configured");
        }
        return shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
    }

    private Url getUrl(String code) {
        return urlRepository.find(code).orElseThrow(() -> new UrlNotFoundException(code));
    }

    public GetOriginalUrlResponse getOriginalUrl(String code) {
        Url url = this.getUrl(code);
        // Update the access count and last accessed time
        this.updateAccessedDetails(code, url.getAccessCount());
        return GetOriginalUrlResponse.builder().originalUrl(url.getOriginalUrl()).build();
    }

    public GetAccessCountResponse getAccessCount(String shortUrl) {
        String code = this.getCode(shortUrl);
        Url url = this.getUrl(code);
        log.info("Access count for short URL {}: {}", shortUrl, url.getAccessCount());
        return GetAccessCountResponse.builder().accessCount(url.getAccessCount()).build();
    }

    public GetLastAccessedResponse getLastAccessedAt(String shortUrl) {
        String code = this.getCode(shortUrl);
        Url url = this.getUrl(code);
        log.info("Last accessed time for short URL {}: {}", shortUrl, url.getLastAccessedAt());
        return GetLastAccessedResponse.builder().lastAccessedAt(url.getLastAccessedAt()).build();
    }

    public ShortUrlResponse createShortUrl(String originalUrl) {
        // Validate the original URL
        originalUrl = CommonUtil.validateAndNormalizeUrl(originalUrl);

        Url url = urlRepository.save(
                Url.builder().originalUrl(originalUrl).code(getNextCode()).build()
        );

        String shortUrl = urlConfig.getBaseUrl() + "/" + url.getCode();
        log.info("Created short URL: {} for original URL: {}", shortUrl, originalUrl);
        return ShortUrlResponse.builder().shortUrl(shortUrl).build();
    }

    public ShortUrlResponse updateShortUrl(String shortUrl, String newOriginalUrl) {
        String code = this.getCode(shortUrl);
        Url url = urlRepository.update(code, newOriginalUrl).orElseThrow(() -> new UrlNotFoundException(shortUrl));

        String updatedShortUrl = urlConfig.getBaseUrl() + "/" + url.getCode();
        log.info("Updated short URL: {} to new original URL: {}", updatedShortUrl, newOriginalUrl);
        return ShortUrlResponse.builder().shortUrl(updatedShortUrl).build();
    }

    public void deleteShortUrl(String shortUrl) {
        String code = this.getCode(shortUrl);
        urlRepository.delete(code).orElseThrow(() -> new UrlNotFoundException(shortUrl));
        log.info("Deleted short URL: {}", shortUrl);
    }

    public GetAccessDetailsResponse getAccessDetails(String shortUrl) {
        String code = this.getCode(shortUrl);
        Url url = this.getUrl(code);
        log.info("Access details for short URL {}: Access Count: {}, Last Accessed At: {}",
                shortUrl, url.getAccessCount(), url.getLastAccessedAt());
        return GetAccessDetailsResponse.builder()
                .accessCount(url.getAccessCount()).lastAccessedAt(url.getLastAccessedAt())
                .build();
    }

    private void updateAccessedDetails(String code, long accessCount) {
        Url url = urlRepository.updateAccessedDetails(code, accessCount);
        log.info("Updated access count: {} and last accessed time: {} for short URL: {}",
                url.getAccessCount(), url.getLastAccessedAt(), urlConfig.getBaseUrl() + "/" + code);
    }

    private String getNextCode() {
        Long id = idGenerator.nextId();
        return EncodeUtil.encodeToBase62(id);
    }

}
