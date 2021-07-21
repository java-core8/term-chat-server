package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.log.Log;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.logging.Level;

public class SendTo implements CommandExecute {
    @Override
    public void execute(ProcessData processData) {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        if(msg.getCommand().equals("to")) {
            try {
                // log
                Log.toLog(SendTo.class, Level.INFO, "запущена команда "
                        + msg.getCommand().equals("to"));

                Message newMessageToPrivate = new MessageBuilder()
                        .setMsg(msg.getMsg())
                        .setTo(Name.PRIVATE.getName())
                        .setFrom(msg.getFrom())
                        .buildMessage();
                clh.sendToUser(msg.getParameter(), JSON.toJsonMessage(newMessageToPrivate));
            } catch (IOException e) {
                Log.logTrow(SendTo.class, "execute", e);
            }
        }
    }
}
