package ru.tcreator.serv;

import ru.tcreator.command.CommandObserver;
import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.SendStatus;
import ru.tcreator.enums.ServAnswer;
import ru.tcreator.json_parser.JSON;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

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
            sendMessageToAllUser(
                JSON.toJson(
                    new MessageBuilder()
                            .setFrom(Name.SERVER.getName())
                            .setMsg(nickname + " " + ServAnswer.CHAT_ON.getAnsver())
                            .buildMessage())
            );

            while(!disconnected) {
                String byClientString = readIn();
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

                } else {
                    // если все проверки на прерывания пройдены включаем блок обработчика комманд на строку.
                    if(msg.isCommand()) {
                        commandObserver.processCommand(processData);
                    } else {
                        sendMessageToAllUser(JSON.toJson(msg));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
