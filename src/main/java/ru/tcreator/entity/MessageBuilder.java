package ru.tcreator.entity;

import ru.tcreator.enums.Name;

public class MessageBuilder {
    protected String msg;
    protected String from;
    protected String command;
    protected String to;
    protected String parameter;

    public MessageBuilder setMsg(String msg) {
        this.msg = msg.trim();

        return this;
    }

    public MessageBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder setFrom(Name name) {
        this.from = name.getName();
        return this;
    }

    public MessageBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    public MessageBuilder setCommand(String command) {
        this.command = command;
        return this;
    }

    public MessageBuilder setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public Message buildMessage() {
        if(from != null && msg != null) {
            if(to != null) {
                return new Message(msg, from, to);
            }
            if(command != null) {
                return new Message(msg, from, command, parameter);
            }
            return new Message(msg, from);
        }
        if(command != null) {
            return new Message(null, null, command, parameter);
        }

        return null;

    }

}
