package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.Paths;
import ru.tcreator.file.ReaderFile;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;

public class Help implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();

        if(msg.getCommand().equals("help")) {
            String helpString = new ReaderFile(Paths.HELP.getPath()).read();
            Message helpMessage = new MessageBuilder()
                    .setMsg(helpString)
                    .setTo(Name.PRIVATE.getName())
                    .setFrom(Name.SERVER)
                    .buildMessage();
            clh.sendToUser(msg.getFrom(), JSON.toJsonMessage(helpMessage));
        }
    }
}
