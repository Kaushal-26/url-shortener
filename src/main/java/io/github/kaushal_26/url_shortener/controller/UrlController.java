package io.github.kaushal_26.url_shortener.controller;

import io.github.kaushal_26.url_shortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@Slf4j
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping
    public ResponseEntity<String> createShortUrl(@RequestParam String originalUrl) {
        log.info("Creating short URL for: {}", originalUrl);
        return new ResponseEntity<>(urlService.createShortUrl(originalUrl).getShortUrl(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateShortUrl(@RequestParam String shortUrl, @RequestParam String newOriginalUrl) {
        log.info("Updating short URL: {} to new original URL: {}", shortUrl, newOriginalUrl);
        return new ResponseEntity<>(urlService.updateShortUrl(shortUrl, newOriginalUrl).getShortUrl(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteShortUrl(@RequestParam String shortUrl) {
        log.info("Deleting short URL: {}", shortUrl);
        urlService.deleteShortUrl(shortUrl);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access-count")
    public ResponseEntity<Long> getAccessCount(@RequestParam String shortUrl) {
        log.info("Getting access count for short URL: {}", shortUrl);
        return new ResponseEntity<>(urlService.getAccessCount(shortUrl), HttpStatus.OK);
    }

    @GetMapping("/last-accessed")
    public ResponseEntity<String> getLastAccessedAt(@RequestParam String shortUrl) {
        log.info("Getting last accessed time for short URL: {}", shortUrl);
        return new ResponseEntity<>(urlService.getLastAccessedAt(shortUrl).toString(), HttpStatus.OK);
    }
}
