package io.github.kaushal_26.url_shortener.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetLastAccessedResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy 'at' hh:mm a UTC", timezone = "UTC")
    private Instant lastAccessedAt;

}
