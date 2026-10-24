package iotlab.test.utils;

public enum Role {
    DOMAIN_ADMIN("DOMAIN_ADMIN"),
    FARMER("FARMER");

    private final String prefix;

    Role(String prefix) {
        this.prefix = prefix;
    }

    public String username() {
        return ConfigReader.get(prefix + "_USERNAME");
    }

    public String password() {
        return ConfigReader.get(prefix + "_PASSWORD");
    }
}
