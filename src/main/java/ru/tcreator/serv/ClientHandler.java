package ru.tcreator.serv;

import ru.tcreator.command.CommandObserver;
import ru.tcreator.command.ProcessData;
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
            CommandObserver commandObserver = new CommandObserver();

            nicknameRequest();
            /**
             * Если канал с пользователем обрывается принудительно до ввода никнейма в чате.
             * не заходим в обработчик
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
                    ProcessData processData = new ProcessData(this, byClientString);
                    /**
                     * Если обрыв спровоцирован закрытием чата после ввода никнейма
                     */
                    if (byClientString == null) {
                        Message errMessage = new MessageBuilder()
                                .setCommand("exit")
                                .setMsg(" ")
                                .setFrom("")
                                .setTo("")
                                .buildMessage();
                        ProcessData errData = new ProcessData(this, errMessage);

                        commandObserver.processCommand(errData);
                    } else {
                        // если все проверки на прерывания пройдены включаем блок обработчика комманд на строку. Если в строке имеется команда. Её надо выполнить
                        // при этом сообщение должно быть отправлено
                        if(byClientString.isCommand()) {
                            commandObserver.processCommand(processData);
                        } else {
                          sendMessageToAllUser(JSON.toJson(byClientString));
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



    // TODO переписать момент приветсвия на сторону клиента. Получается очень странная туфта.
    // Первый щапрос должен быть с ником

    protected void nicknameRequest() throws IOException {
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
