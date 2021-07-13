package ru.tcreator.entity;


//TODO некоторые поля лишние, снести как будет время
//класс сам много генерирует


public class MessageBuilder {
    protected String msg;
    protected String time;
    protected long timeStamp;
    protected String formatTime = "hh:mm:ss a";
    protected String from;
    protected String to = "all";
    protected boolean isCommand = Boolean.FALSE;
    protected String command;
    protected SendStatus status;

    public MessageBuilder setMsg(String msg) {
        String trimMessage = msg.trim() + "1";
        if(trimMessage.length() == 1) {
            status = SendStatus.GAP;
        } else {
            this.msg = msg.trim();
        }
        return this;
    }

    public MessageBuilder setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public MessageBuilder setFormatTime(String formatTime) {
        this.formatTime = formatTime;
        return this;
    }

    public MessageBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public MessageBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    public MessageBuilder setCommand(boolean command) {
        isCommand = command;
        return this;
    }

    public MessageBuilder setCommand(String command) {
        this.command = command;
        return this;
    }

    public MessageBuilder setStatus(SendStatus status) {
        this.status = status;
        return this;
    }

    public Message buildMessage() {
        if(from != null && msg != null) {
            if(to != null) {
                return new Message(msg, from, to);
            }
            return new Message(msg, from);
        }

        return null;

    }

}
