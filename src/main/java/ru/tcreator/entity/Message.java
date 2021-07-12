package ru.tcreator.entity;

import ru.tcreator.inerfaces.MessageEntityies;

public class Message implements MessageEntityies {
    protected String msg;
    private Message(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

    public static Message getMessage(String msg ) {
        return new Message(msg);
    }

    @Override
    public boolean isNull() {
        return msg.equals("null");
    }
}
