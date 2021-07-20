package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.ServAnswer;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;

/**
 * Отклик на команду exit
 * Устанавливает флаг disconnect в False
 * Рассылает всем сообщение о выходе из чата
 */
public class Exit implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();

        if(msg.getCommand().equals("exit")) {
            String logOffToAll = JSON.toJsonMessage(
                    new MessageBuilder()
                            .setFrom(Name.SERVER.getName())
                            .setMsg(msg.getFrom() + " " + ServAnswer.CHAT_OFF.getAnsver())
                            .buildMessage());
            // Если команда была в строке сообщения, сообщение нужно доставить.
            if(msg.getMsg() != null) {
                Message allTo = new MessageBuilder()
                        .setFrom(msg.getFrom())
                        .setMsg(msg.getMsg())
                        .buildMessage();
                clh.sendMessageToAllUser(JSON.toJsonMessage(allTo));
                // Говорим всем, что пользователь вышел.
                clh.sendMessageToAllUser(logOffToAll);
                // Пишем сообщение пользователя в файл лога
                JSON.addMessageFile(allTo);
            } else {
                clh.sendMessageToAllUser(logOffToAll);
            }
            clh.setDisconnected();
            clh.close();
            clh.removeMeInBase(clh);
        }
    }
}
