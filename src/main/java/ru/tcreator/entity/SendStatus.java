package ru.tcreator.entity;

public enum SendStatus {
    GOOD("alright"),
    BAD("bad request"),
    GAP("only gap message"),
    OFF("socket is close");


    String statusMsg;
    SendStatus(String status) {
        statusMsg = status;
    }

    String getMessage() {
        return statusMsg;
    }
}
