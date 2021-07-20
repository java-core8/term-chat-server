package ru.tcreator.enums;

public enum Name {
    SERVER("SERVER"),
    PRIVATE("PRIVATE"),
    ALL("ALL");

    String name;

    Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
