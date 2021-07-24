package ru.tcreator.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    protected String msg;
    protected String time;
    protected long timeStamp;
    protected String formatTime = "hh:mm:ss a";
    protected String from;
    protected String to = "all";
    protected boolean isCommand = Boolean.FALSE;
    protected String command;
    protected String parameterCommand;


    public Message(String msg, String from) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
    }

    public Message(String msg, String from, String command, String parameterCommand) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.command = command;
        this.isCommand = Boolean.TRUE;
        this.parameterCommand = parameterCommand;
    }

    public Message(String msg, String from, String to) {
        this.timeStamp = new Date().getTime();
        DateFormat dateFormat = new SimpleDateFormat(formatTime);
        this.msg = msg;
        this.time = dateFormat.format(new Date(timeStamp));
        this.from = from;
        this.to = to;
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

    public String getParameter() {
        return parameterCommand;
    }

}

