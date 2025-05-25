package io.github.kaushal_26.url_shortener.controller;

import io.github.kaushal_26.url_shortener.dto.CreateShortUrlRequest;
import io.github.kaushal_26.url_shortener.dto.ShortUrlRequest;
import io.github.kaushal_26.url_shortener.dto.UpdateShortUrlRequest;
import io.github.kaushal_26.url_shortener.response.*;
import io.github.kaushal_26.url_shortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
    public ResponseEntity<ShortUrlResponse> createShortUrl(@NotNull @RequestBody CreateShortUrlRequest payload) {
        log.info("Creating short URL for: {}", payload.getOriginalUrl());
        return new ResponseEntity<>(urlService.createShortUrl(payload.getOriginalUrl()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ShortUrlResponse> updateShortUrl(@NotNull @RequestBody UpdateShortUrlRequest payload) {
        log.info("Updating short URL: {} to new original URL: {}", payload.getShortUrl(), payload.getNewOriginalUrl());
        return ResponseEntity.ok(urlService.updateShortUrl(payload.getShortUrl(), payload.getNewOriginalUrl()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteShortUrl(@NotNull @RequestBody ShortUrlRequest payload) {
        log.info("Deleting short URL: {}", payload.getShortUrl());
        urlService.deleteShortUrl(payload.getShortUrl());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/access-count")
    public ResponseEntity<GetAccessCountResponse> getAccessCount(@NotNull @RequestBody ShortUrlRequest payload) {
        log.info("Getting access count for short URL: {}", payload.getShortUrl());
        return ResponseEntity.ok(urlService.getAccessCount(payload.getShortUrl()));
    }

    @GetMapping("/last-accessed")
    public ResponseEntity<GetLastAccessedResponse> getLastAccessedAt(@NotNull @RequestBody ShortUrlRequest payload) {
        log.info("Getting last accessed time for short URL: {}", payload.getShortUrl());
        return ResponseEntity.ok(urlService.getLastAccessedAt(payload.getShortUrl()));
    }

    @GetMapping("/access-details")
    public ResponseEntity<GetAccessDetailsResponse> getAccessDetails(@NotNull @RequestBody ShortUrlRequest payload) {
        log.info("Getting access details for short URL: {}", payload.getShortUrl());
        return ResponseEntity.ok(urlService.getAccessDetails(payload.getShortUrl()));
    }

    @GetMapping("/{code}")
    public ResponseEntity<GetOriginalUrlResponse> getOriginalUrl(@PathVariable String code) {
        log.info("Getting original URL for: {}", code);
        return ResponseEntity.ok(urlService.getOriginalUrl(code));
    }

}
