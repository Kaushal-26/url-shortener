package io.github.kaushal_26.url_shortener.controller;

import io.github.kaushal_26.url_shortener.dto.CreateShortUrlRequest;
import io.github.kaushal_26.url_shortener.dto.ShortUrlRequest;
import io.github.kaushal_26.url_shortener.dto.UpdateShortUrlRequest;
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

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<String> createShortUrl(@RequestBody CreateShortUrlRequest payload) {
        log.info("Creating short URL for: {}", payload.getOriginalUrl());
        return new ResponseEntity<>(
                urlService.createShortUrl(payload.getOriginalUrl()).getShortUrl(),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<String> updateShortUrl(@RequestBody UpdateShortUrlRequest payload) {
        log.info("Updating short URL: {} to new original URL: {}", payload.getShortUrl(), payload.getNewOriginalUrl());
        return new ResponseEntity<>(
                urlService.updateShortUrl(payload.getShortUrl(), payload.getNewOriginalUrl()).getShortUrl(),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteShortUrl(@RequestBody ShortUrlRequest payload) {
        log.info("Deleting short URL: {}", payload.getShortUrl());
        urlService.deleteShortUrl(payload.getShortUrl());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access-count")
    public ResponseEntity<Long> getAccessCount(@RequestBody ShortUrlRequest payload) {
        log.info("Getting access count for short URL: {}", payload.getShortUrl());
        return new ResponseEntity<>(urlService.getAccessCount(payload.getShortUrl()), HttpStatus.OK);
    }

    @GetMapping("/last-accessed")
    public ResponseEntity<String> getLastAccessedAt(@RequestBody ShortUrlRequest payload) {
        log.info("Getting last accessed time for short URL: {}", payload.getShortUrl());
        return new ResponseEntity<>(urlService.getLastAccessedAt(payload.getShortUrl()).toString(), HttpStatus.OK);
    }

}
