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
            /**
             * Если канал с пользователем обрывается принудительно до ввода никнейма в чате.
             */
            if(!disconnected) {
                sendMessageToAllUser(Message.getMessage("К серверу подключился " + nickname));
                while(!disconnected) {
                    MessageEntityies byClientString = readIn();
                    /**
                     * Если обрыв спровоцирован закрытием чата после ввода никнейма
                     */
                    if (byClientString.isNull()) {
                        sendMessageToAllUser(Message.getMessage(nickname + " отключился от сервера"));
                        setDisconnected();
                    } else {
                        //TODO переписать блок обработки команд
                        // вот этот голимак заменить на блок
                        // если все проверки на прерывания пройдены включаем блок обработчика комманд на строку. Если в строке имеется команда. Её надо выполнить
                        // при этом сообщение должно быть отправлено
                        if(byClientString.toString().trim().equals("/exit")) {
                            sendMessageToAllUser(Message.getMessage(nickname + " отключился от сервера"));
                            setDisconnected();
                        } else {
                            sendMessageToAllUser(Message.getMessage(nickname + ": " + byClientString));
                        }
                    }

                }
                /**
                 * обрыв соединения удаление из спискаПользователей {@link ClientMap}
                 */
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
