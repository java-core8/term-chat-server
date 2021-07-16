package ru.tcreator.serv;
import ru.tcreator.clientmap.ClientList;
import ru.tcreator.enums.Paths;
import ru.tcreator.settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() {
        try {
            Settings settings = new Settings(Paths.SETTINGS.getPath());
            final int PORT = Integer.parseInt(settings.getProperties("PORT"));

            ClientList clientList = ClientList.getInstance();
            ServerSocket server = new ServerSocket(PORT);
            while(true) {
                Socket clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
                clientList.add(clientHandler);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
