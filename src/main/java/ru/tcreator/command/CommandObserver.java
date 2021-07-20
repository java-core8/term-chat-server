package ru.tcreator.command;

import ru.tcreator.command.list.*;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommandObserver {
    protected ClientHandler clientHandler;
    final protected List<CommandExecute> commandList = Arrays.asList(
            (executive -> new Exit().execute(executive)),
            (executive -> new SendTo().execute(executive)),
            (executive -> new ListUser().execute(executive)),
            (executive -> new History().execute(executive)),
            (executive -> new Help().execute(executive))
    );

    public void processCommand(ProcessData data) throws IOException {
        for (CommandExecute command : commandList) {
            command.execute(data);
        }
    }
}
