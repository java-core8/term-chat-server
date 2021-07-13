package ru.tcreator.serv;
import ru.tcreator.clientmap.ClientMap;
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

            ClientMap clientMap = ClientMap.getInstance();
            ServerSocket server = new ServerSocket(PORT);
            while(true) {
                Socket clientSocket = server.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
                clientMap.add(clientHandler);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
