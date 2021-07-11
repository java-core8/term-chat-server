package ru.tcreator.enums;

public enum Paths {
    DEFAULT_SETTINGS("src/main/resources/inet_settings.properties");

    private final String path;

    Paths(String s) {
        this.path = s;
    }

    public String getPath() {
        return path;
    }
}
