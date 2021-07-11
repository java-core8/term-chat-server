package ru.tcreator.serv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    @Override
    public void run() {
        int port = 10156;
        try {
            ServerSocket server = new ServerSocket(port);
            Socket clientSocket = server.accept();

            ClientHandler clientHandler = new ClientHandler(clientSocket);
//            clientList.add(clientSocket);
            new Thread(clientHandler).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
