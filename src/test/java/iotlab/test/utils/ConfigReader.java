package iotlab.test.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties defaults = load("config.properties", true);
    private static final Properties local = load("config.local.properties", false);

    private static Properties load(String fileName, boolean required) {
        Properties props = new Properties();
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                if (required) throw new IllegalStateException("Không tìm thấy " + fileName);
            } else {
                props.load(new java.io.InputStreamReader(in, java.nio.charset.StandardCharsets.UTF_8));
            }
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Lỗi đọc " + fileName, e);
        }
        return props;
    }

    private static String lookup(String key) {
        String value = System.getenv(key);
        if (isBlank(value)) value = System.getProperty(key);
        if (isBlank(value)) value = local.getProperty(key);
        if (isBlank(value)) value = defaults.getProperty(key);
        return isBlank(value) ? null : value.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String get(String key) {
        String value = lookup(key);
        if (value == null) {
            throw new IllegalStateException("Thiếu cấu hình bắt buộc: " + key
                    + " (đặt trong config.local.properties hoặc biến môi trường)");
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = lookup(key);
        return value == null ? defaultValue : value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = lookup(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }
}
