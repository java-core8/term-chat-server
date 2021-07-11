package ru.tcreator.serv;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    protected ArrayList<Socket> clientList = new ArrayList<>();
    @Override
    public void run() {
        int port = 10156;
        try {
            ServerSocket server = new ServerSocket(port);
            Socket clientSocket = server.accept();
            clientList.add(clientSocket);
            new Thread(new ClientHandler(clientSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {

        }
    }
}
