package ru.tcreator.serv;

import ru.tcreator.clientmap.ClientMap;
import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

public class ClientHandler extends ServerHandlerAbstract implements Runnable  {
    protected String nickname;

    public ClientHandler(Socket clSocket) throws IOException {
        super(clSocket);
        firstConnect();
    }

    @Override
    public void run() {
        try {
            sendMessageToAllUser(Message.getMessage("К серверу подключился " + nickname));
            while(socket.isConnected()) {
                MessageEntityies byClientString = readIn();
                sendMessageToAllUser(Message.getMessage(nickname + ": " + byClientString));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    protected void firstConnect() throws IOException {
        ClientMap clientMap = ClientMap.getInstance();

        writeOut(Message.getMessage("Введите никнейм"));

        MessageEntityies readNickName = readIn();
        nickname = readNickName.toString();
    }

}
