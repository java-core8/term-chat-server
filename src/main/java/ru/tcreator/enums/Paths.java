package ru.tcreator.enums;

public enum Paths {
    SETTINGS("src/main/resources/inet_settings.properties"),
    ROOT_PATH("src/main/ru/tcreator/");

    private final String path;

    Paths(String s) {
        this.path = s;
    }

    public String getPath() {
        return path;
    }
}
