package ru.tcreator.entity;

import ru.tcreator.inerfaces.MessageEntityies;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements MessageEntityies {
    protected String msg;
    protected String time;
    protected long timeStamp;
    protected String formatTime = "hh:mm:ss a";
    protected String from;
    protected String to = "all";
    protected boolean isCommand = Boolean.FALSE;
    protected String command;
    protected SendStatus status;
    protected String statusMessage;

    public Message(SendStatus status, String msg, String from) {
        this.status = status;
        statusMessage = status.getMessage();
        this.msg = msg;
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.timeStamp = new Date().getTime();
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
    }

    public Message(String msg, String from) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        status = SendStatus.GOOD;
        this.statusMessage = status.getMessage();
    }

    public Message(String msg, String from, String to) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
        status = SendStatus.GOOD;
        this.statusMessage = status.getMessage();
    }

    public Message(String msg, String from, String command, String to) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
        this.command = command;
        this.isCommand = Boolean.TRUE;
        status = SendStatus.GOOD;
        this.statusMessage = status.getMessage();
    }

    public Message(String msg, String from, String command, String to, SendStatus status) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
        this.command = command;
        this.isCommand = Boolean.TRUE;
        this.status = status;
        this.statusMessage = status.getMessage();
    }

    public Message(String msg, String from, String command, String to, String formatTime) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
        this.command = command;
        this.isCommand = Boolean.TRUE;
        status = SendStatus.GOOD;
        this.formatTime = formatTime;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        String toMessagePattern = "[всем]";
        if(!to.equals("all")) {
            toMessagePattern = "[" + to + "]";
        }
        return toMessagePattern + " " + from + " " + time + ": " + msg;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isCommand() {
        return isCommand;
    }

    public String getCommand() {
        return command;
    }

    public SendStatus getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }


}
