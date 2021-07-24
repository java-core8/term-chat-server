package ru.tcreator.enums;

public enum ServAnswer {

    CHAT_OFF("вышел из чата"),
    CHAT_ON("подключился к чату"),
    NOT_VALID_MSG("запрос не валиден");

    String name;

    ServAnswer(String name) {
        this.name = name;
    }

    public String getAnsver() {
        return name;
    }
}
