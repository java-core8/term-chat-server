package ru.tcreator;

import ru.tcreator.serv.Server;

public class Start {
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
