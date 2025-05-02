package io.github.kaushal_26.url_shortener.util;

import io.github.kaushal_26.url_shortener.exception.UrlValidationError;

import java.net.URI;
import java.net.URISyntaxException;

public class CommonUtil {

    private static boolean isValidUrl(String url) {

        // Basic URL validation logic
        if (url == null) {
            return false;
        }
        if (!(url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://"))) {
            return false;
        }

        // Use java net uri class to check if the url is valid
        try {
            URI uri = new URI(url);
            // Check if the URI has a scheme (protocol)
            return uri.getScheme() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private static String normalizeUrl(String url) {
        String trimmedUrl = url.trim();
        // Remove multiple slashes
        trimmedUrl = trimmedUrl.replaceAll("(?<!:)/{2,}", "/");
        // Remove trailing slashes
        trimmedUrl = trimmedUrl.replaceAll("/+$", "");
        return trimmedUrl;
    }

    public static String validateAndNormalizeUrl(String url) {
        if (!isValidUrl(url)) {
            throw new UrlValidationError(url, "URL is not valid");
        }
        return normalizeUrl(url);
    }

    public static void main(String[] args) {
        String[] testURLs = {
                "https://www.example.com//",
                "http://localhost:8080///xyz//rbreuge///////rtsjknsrtkjrt///hbtrbjghrtb/oieuwytrqwewret////",
                "http://192.168.1.1",
                "https://192.168.1.1:8443//",
                "http://[2001:db8:85a3:8d3:1319:8a2e:370:7348]", // IPv6
                "https://[2001:db8::1]:8443//", // IPv6 with port
                "ftp://192.168.1.1",          // Non-HTTP protocol (will be invalid)
                "not a url",
                "http:/missing-slash.com",
                "http://example.com//path//to////resource///?query=string&",
        };

        for (String url : testURLs) {
            boolean isValid = isValidUrl(url);
            System.out.println(url + " is " + (isValid ? "valid" : "invalid"));
            if (isValid) {
                System.out.println("--- Trimmed URL: " + normalizeUrl(url) + " ---");
            }
        }
    }

}
