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

    public Message(String msg, String from) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        status = SendStatus.GOOD;
    }

    public Message(String msg, String from, String to) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
        status = SendStatus.GOOD;
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


    @Override
    public String toString() {
        String toMessagePattern = "[всем]";
        if(!to.equals("all")) {
            toMessagePattern = "[" + to + "]";
        }
        return toMessagePattern + " " + from + " " + time + ": " + msg;
    }

    @Override
    public boolean isNull() {
        return msg.equals("null");
    }
}
