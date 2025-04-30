package io.github.kaushal_26.url_shortener.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "urls")
public class Url {
    // The original URL that the user wants to shorten
    private String originalUrl;
    // The shortened code that will be used to access the original URL
    private String shortUrl;

    // Count of how many times the shortened URL has been accessed
    private long accessCount = 0;
    // The date and time when the shortened URL was last accessed
    private Instant lastAccessedAt;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private Instant deletedAt;
}
