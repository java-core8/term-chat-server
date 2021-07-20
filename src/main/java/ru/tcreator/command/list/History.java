package ru.tcreator.command.list;

import ru.tcreator.command.ProcessData;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;
import ru.tcreator.enums.Name;
import ru.tcreator.inerfaces.CommandExecute;
import ru.tcreator.json_parser.JSON;
import ru.tcreator.serv.ClientHandler;

import java.io.IOException;
import java.util.List;

public class History implements CommandExecute {
    @Override
    public void execute(ProcessData processData) throws IOException {
        Message msg = processData.getMessage();
        ClientHandler clh = processData.getClientHandler();
        if(msg.getCommand().equals("history")) {
            try {
                int parameter = Integer.parseInt(msg.getParameter());

                List<Message> messageList = JSON.readMessageSource();
                int limit = parameter > messageList.size() ? 0 : messageList.size() - parameter;
                String writeToMsg = parameter > messageList.size() ? String.valueOf(messageList.size()) : msg.getParameter();
                StringBuilder historyStringBuilder = new StringBuilder("последние ")
                        .append(writeToMsg)
                        .append(" записей\n");

                messageList.stream()
                        .skip(limit)
                        .forEach(message -> {
                            historyStringBuilder.append(message).append("\n");
                        });

                Message newMessageWithListUser = new MessageBuilder()
                        .setMsg(historyStringBuilder.toString())
                        .setTo(Name.PRIVATE.getName())
                        .setFrom(Name.SERVER)
                        .buildMessage();

                clh.sendToUser(msg.getFrom(), JSON.toJsonMessage(newMessageWithListUser));
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
