package ru.tcreator.enums;

public enum Name {
    SERVER("server");

    String name;

    Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}