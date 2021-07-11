package ru.tcreator.serv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() {
        int port = 10156;
        while(true) {
            try {
                ServerSocket server = new ServerSocket(port);
                Socket clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
