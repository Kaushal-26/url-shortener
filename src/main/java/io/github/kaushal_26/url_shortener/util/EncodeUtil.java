package io.github.kaushal_26.url_shortener.util;

public class EncodeUtil {

    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encodeToBase62(long number) {
        StringBuilder encoded = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % BASE62_CHARS.length());
            encoded.insert(0, BASE62_CHARS.charAt(remainder));
            number /= BASE62_CHARS.length();
        }

        return encoded.toString();
    }

}
