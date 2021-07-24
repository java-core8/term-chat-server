package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.enums.Paths;
import ru.tcreator.file.ReaderFile;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.log.Log;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.logging.Level;

public class Help implements CommandExecute {
    @Override
    public void execute(ProcessData processData) {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        try {
            if (msg.getCommand().equals("help")) {
                Log.logger.log(Level.INFO, "запущена команда "
                        + msg.getCommand());
                String helpString = new ReaderFile(Paths.HELP.getPath()).read();
                Message helpMessage = new MessageBuilder()
                        .setMsg(helpString)
                        .setTo(Name.PRIVATE.getName())
                        .setFrom(Name.SERVER)
                        .buildMessage();
                clh.sendToUser(msg.getFrom(), JSON.toJsonMessage(helpMessage));
            }
        } catch (IOException e) {
            Log.logger.throwing(Help.class.getName(), "execute", e);
        }
    }
}
