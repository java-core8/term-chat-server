package ru.tcreator.enums;

public enum ServAnswer {

    CHAT_OFF("вышел из чата");

    String name;

    ServAnswer(String name) {
        this.name = name;
    }

    public String getAnsver() {
        return name;
    }
}
