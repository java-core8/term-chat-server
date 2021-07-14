package ru.tcreator.inerfaces;

import ru.tcreator.command.ProcessData;

import java.io.IOException;

public interface CommandExecute {
    void execute(ProcessData processData) throws IOException;
}
