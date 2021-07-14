package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;

public class SendTo implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        String command = msg.getCommand();
        if(command.startsWith("to")) {

            String[] commandParam = command.split("=");
            if(commandParam.length > 1 && !commandParam[1].equals(" ")) {
                clh.sendToUser(commandParam[1], JSON.toJson(msg));
            }
        }
    }
}
