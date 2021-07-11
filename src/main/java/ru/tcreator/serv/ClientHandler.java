package ru.tcreator.serv;

import ru.tcreator.clientmap.ClientMap;
import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends ServerHandlerAbstract implements Runnable  {
    protected String nickname;

    public ClientHandler(Socket clSocket) throws IOException {
        super(clSocket);
        System.out.println("1");
        firstConnect();

    }

    @Override
    public void run() {
        try {

            writeOut(Message.getMessage("К серверу подключился " + nickname));
            while(socket.isConnected()) {
                MessageEntityies byClient = readIn();
                writeOut(Message.getMessage("Вы написали серверу: " + byClient));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void firstConnect() throws IOException {
        ClientMap clientMap = ClientMap.getInstance();

        writeOut(Message.getMessage("Введите никнейм"));

        MessageEntityies readNickName = readIn();
        System.out.println("11");
        nickname = readNickName.toString();
        if(nickname != null) {
            clientMap.add(nickname, this);
        }
    }
//    protected boolean isClientMapExisxts(String nickname) {
//
//    }
}
