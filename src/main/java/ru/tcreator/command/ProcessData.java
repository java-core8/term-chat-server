package ru.tcreator.command;

import ru.tcreator.entity.Message;
import ru.tcreator.serv.ClientHandler;

public record ProcessData(ClientHandler clientHandler, Message message) {

    public Message getMessage() {
        return message;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
