package ru.tcreator;

import ru.tcreator.serv.Server;

import java.io.IOException;

public class Start {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}
