package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.ServAnswer;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.log.Log;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Отклик на команду exit
 * Устанавливает флаг disconnect в False
 * Рассылает всем сообщение о выходе из чата
 */
public class Exit implements CommandExecute {
    @Override
    public void execute(ProcessData processData) {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        if(msg.getCommand().equals("exit")) {
            try {
                // лог
                Log.logger.log(Level.INFO, "запущена команда "
                        + msg.getCommand());
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
                    // доставляем сообщение пользователя
                    clh.sendMessageToAllUser(JSON.toJsonMessage(allTo));
                    // Пишем сообщение пользователя в файл истории сообщений
                    JSON.addMessageFile(allTo);
                    // Говорим всем, что пользователь вышел.
                }
                clh.sendMessageToAllUser(logOffToAll);

                clh.setDisconnected();
                clh.close();
                clh.removeMeInBase(clh);
                Log.logger.log(Level.OFF, "команда успешно выполнена "
                        + msg.getCommand());
            } catch (IOException e) {
                Log.logger.throwing(ClientHandler.class.getName(), "run", e);
            }

        }
    }
}
