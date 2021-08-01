package ru.tcreator.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tcreator.entity.Message;
import ru.tcreator.entity.MessageBuilder;

public class TestCommandObserver {
    @Test
    @DisplayName("Верное направление выполнение команды")
    void directionTest() {
        CommandObserver commandObserver = new CommandObserver();

        Message testMessage = Mockito.spy(new MessageBuilder()
                .setTo("to")
                .setCommand("exit")
                .setFrom("from")
                .buildMessage()
        );


        //commandObserver.processCommand();
    }
}