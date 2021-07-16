package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;

public class SendTo implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        if(msg.getCommand().equals("to")) {
            Message newMessageToPrivate = new MessageBuilder()
                    .setMsg(msg.getMsg())
                    .setTo(Name.PRIVATE.getName())
                    .setFrom(msg.getFrom())
                    .buildMessage();
            clh.sendToUser(msg.getParameter(), JSON.toJson(newMessageToPrivate));
        }
    }
}
