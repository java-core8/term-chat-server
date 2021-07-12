package ru.tcreator.serv;
import ru.tcreator.clientmap.ClientMap;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() {
        int port = 10156;
            try {
                ClientMap clientMap = ClientMap.getInstance();
                ServerSocket server = new ServerSocket(port);
                while(true) {
                    Socket clientSocket = server.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    new Thread(clientHandler).start();
                    clientMap.add(clientHandler);
                }
            } catch (IOException e) {

            }

    }
}
