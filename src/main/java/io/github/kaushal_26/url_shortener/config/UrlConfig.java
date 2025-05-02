package io.github.kaushal_26.url_shortener.config;

import io.github.kaushal_26.url_shortener.util.CommonUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UrlConfig {

    private final String baseUrl;

    public UrlConfig(@Value("${url.shortener.base-url}") String baseUrl) {
        this.baseUrl = CommonUtil.validateAndNormalizeUrl(baseUrl);
    }

}
