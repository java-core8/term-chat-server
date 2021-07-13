package ru.tcreator.serv;

import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.json_parser.JSON;

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
                sendMessageToAllUser(
                    JSON.toJson(
                        new MessageBuilder()
                                .setFrom("server")
                                .setMsg(nickname + " подключился к серверу")
                                .buildMessage())
                );
                while(!disconnected) {
                    Message byClientString = JSON.fromJsonMessage(readIn());
                    /**
                     * Если обрыв спровоцирован закрытием чата после ввода никнейма
                     */
                    if (byClientString == null) {
                        sendMessageToAllUser(
                                JSON.toJson(
                                    new MessageBuilder()
                                        .setFrom("server")
                                        .setMsg(nickname + " отключился от сервера")
                                        .buildMessage())
                            );
                        setDisconnected();
                    } else {
                        //TODO переписать блок обработки команд
                        // вот этот голимак заменить на блок

                        // если все проверки на прерывания пройдены включаем блок обработчика комманд на строку. Если в строке имеется команда. Её надо выполнить
                        // при этом сообщение должно быть отправлено
                        if( byClientString.isCommand()) {
                            if (byClientString.getCommand().equals("exit")) {
                                // TODO прописать обработчик команд нормальный ( паттерн команда подойдёт)
                                // а то это позор
                                if(byClientString.getMsg() != null) {
                                    sendMessageToAllUser(
                                            JSON.toJson(
                                                    new MessageBuilder()
                                                            .setFrom(nickname)
                                                            .setMsg(byClientString.getFrom() + " отключился от сервера")
                                                            .buildMessage())
                                    );
                                }

                                sendMessageToAllUser(
                                    JSON.toJson(
                                        new MessageBuilder()
                                            .setFrom("server")
                                            .setMsg(byClientString.getFrom() + " отключился от сервера")
                                            .buildMessage())
                                );
                                setDisconnected();
                            }

                        } else {
                            if(byClientString.getTo().equals("all")) {
                                sendMessageToAllUser(JSON.toJson(byClientString));
                            }
                        }
                    }

                }
                /**
                 * обрыв соединения, удаление из спискаПользователей {@link ClientMap}
                 */
                close();
                removeMeInBase(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void firstConnect() throws IOException {
        Message builder = new MessageBuilder()
                .setFrom("server")
                .setMsg("Введите никнейм чтобы продолжить!")
                .setTo("private")
                .buildMessage();

        writeOut(JSON.toJson(builder));
        String readString = readIn();
        Message readNickName = JSON.fromJsonMessage(readString);
        if(readNickName == null) {
            setDisconnected();
        }
        nickname = readNickName.getFrom();
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
