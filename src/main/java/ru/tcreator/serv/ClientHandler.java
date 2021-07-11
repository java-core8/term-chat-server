package ru.tcreator.serv;

import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends ServerHandlerAbstract implements Runnable  {

    public ClientHandler(Socket clSocket) throws IOException {
        super(clSocket);
    }

    @Override
    public void run() {
        try {
            writeOut(Message.getMessage("Подключился новый пользователь: " + socket.getPort()));
            while(socket.isConnected()) {
                MessageEntityies byClient = readIn();
                writeOut(Message.getMessage("Вы написали серверу: " + byClient));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
