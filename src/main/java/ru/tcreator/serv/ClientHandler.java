package ru.tcreator.serv;

import ru.tcreator.Start;
import ru.tcreator.command.CommandObserver;
import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.ServAnswer;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.log.Log;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class ClientHandler extends ServerHandlerAbstract implements Runnable  {
    protected String nickname;
    protected long code;


    public ClientHandler(Socket clSocket) throws IOException {
        super(clSocket);

        code = System.currentTimeMillis();
        nickname = String.valueOf(code);
    }

    @Override
    public void run() {
        try {
            CommandObserver commandObserver = new CommandObserver();
            String readString = readIn();
            Message clientResponse = JSON.fromJsonMessage(readString);
            //устанавливаем ник для поиска при рассылке сообщений пользователю через оманду to
            nickname = clientResponse.getFrom();
            //лог
            Log.logger.log(Level.INFO, "Установлен ник нового пользователя " + nickname);

            sendMessageToAllUser(
                JSON.toJsonMessage(
                    new MessageBuilder()
                            .setFrom(Name.SERVER.getName())
                            .setMsg(nickname + " " + ServAnswer.CHAT_ON.getAnsver())
                            .buildMessage())
            );

            while(!disconnected) {
                try {
                    String byClientString = readIn();
                    System.out.println(nickname);
                    System.out.println(byClientString);
                    Message msg = JSON.fromJsonMessage(byClientString);
                    ProcessData processData = new ProcessData(this, msg);
                    /**
                     * Если обрыв спровоцирован закрытием чата
                     */
                    if (byClientString == null) {

                        Message errMessage = new MessageBuilder()
                                .setCommand("exit")
                                .setFrom(nickname)
                                .buildMessage();
                        ProcessData errData = new ProcessData(this, errMessage);
                        commandObserver.processCommand(errData);
                        //лог
                        Log.logger.log(Level.OFF, "Экстренный выход клиента");
                    } else {
                        // если все проверки на прерывания пройдены включаем блок обработчика комманд на строку.
                        if (msg.isCommand()) {
                            commandObserver.processCommand(processData);
                        } else {
                            sendMessageToAllUser(JSON.toJsonMessage(msg));
                            JSON.addMessageFile(msg);
                        }
                    }
                } catch (Exception e) {
                  Log.logger.throwing(ClientHandler.class.getName(), "run", e);
                }
            }
            //лог
            Log.logger.log(Level.INFO, "клиент закончил работу");
        } catch (IOException e) {
            Log.logger.throwing(ClientHandler.class.getName(), "run", e);
        }
    }

    public String getNickname() {
        return nickname;
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
