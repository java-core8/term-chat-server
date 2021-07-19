package ru.tcreator.command.list;

import ru.tcreator.clientmap.ClientList;
import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.Iterator;

public class ListUser implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        if(msg.getCommand().equals("list")) {
            StringBuilder nicknameList = new StringBuilder("Список участников чата:\n");
            Iterator<ClientHandler> iterator = ClientList
                    .getInstance()
                    .getIterator();
            while (iterator.hasNext()) {
                ClientHandler clientHandler = iterator.next();
                nicknameList.append(clientHandler.getNickname()).append("\n");
            }

            Message newMessageWithListUser = new MessageBuilder()
                    .setMsg(nicknameList.toString())
                    .setTo(Name.PRIVATE.getName())
                    .setFrom(Name.SERVER)
                    .buildMessage();

            clh.sendToUser(msg.getFrom(), JSON.toJsonMessage(newMessageWithListUser));
        }
    }
}
