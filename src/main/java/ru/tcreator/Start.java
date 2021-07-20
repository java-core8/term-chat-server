package ru.tcreator;
import ru.tcreator.serv.Server;

public class Start {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

