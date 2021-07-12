package ru.tcreator.serv;

import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler extends ServerHandlerAbstract implements Runnable  {
    protected String nickname;
    protected long code;


    public ClientHandler(Socket clSocket) throws IOException {
        super(clSocket);

        // устанавливаем дефолтное значение для сравнения объекта,
        // если никнейм ещё не назначен, а пользватель закрыл чат.
        code = System.currentTimeMillis();
        nickname = String.valueOf(code);
    }

    @Override
    public void run() {
        try {
            firstConnect();
            if(!disconnected) {
                sendMessageToAllUser(Message.getMessage("К серверу подключился " + nickname));
                while(!disconnected) {
                    MessageEntityies byClientString = readIn();
                    if (byClientString.isNull()) {
                        sendMessageToAllUser(Message.getMessage(nickname + " отключился от сервера"));
                        setDisconnected();
                    } else {
                        if(byClientString.toString().trim().equals("/exit")) {
                            sendMessageToAllUser(Message.getMessage(nickname + " отключился от сервера"));
                            setDisconnected();
                        } else {
                            sendMessageToAllUser(Message.getMessage(nickname + ": " + byClientString));
                        }
                    }

                }
                close();
                removeMeInBase(this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void firstConnect() throws IOException {
        writeOut(Message.getMessage("Введите никнейм"));
        MessageEntityies readNickName = readIn();
        if(readNickName.toString() == null) {
            close();
            removeMeInBase(this);
            setDisconnected();
        }
        nickname = readNickName.toString();
        setStartFlag();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname + code);
    }



}
