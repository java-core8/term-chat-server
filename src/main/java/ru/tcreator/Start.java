package ru.tcreator;
import ru.tcreator.enums.Paths;
import ru.tcreator.serv.Server;
import ru.tcreator.settings.Settings;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Properties;


public class Start {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}

