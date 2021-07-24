package ru.tcreator;
import ru.tcreator.log.Log;
import ru.tcreator.serv.Server;

import java.util.logging.Level;

public class Start {


    public static void main(String[] args) {
        Log.logger.log(Level.INFO, "Запуск системы");
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

