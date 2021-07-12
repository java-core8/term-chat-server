package ru.tcreator.clientmap;

import ru.tcreator.serv.ClientHandler;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientMap {
    private CopyOnWriteArrayList<ClientHandler> clientList;
    static private ClientMap thisClientInstance;
    private ClientMap() {
        clientList = new CopyOnWriteArrayList<>();
    }

    public static ClientMap getInstance() {
        if(thisClientInstance == null) {
            thisClientInstance = new ClientMap();
        }
        return thisClientInstance;
    }

    public void add(ClientHandler clientEntity) {
        clientList.add(clientEntity);
    }

    public void remove(ClientHandler clientEntity) {
        clientList.remove(clientEntity);
    }

    public Iterator<ClientHandler> getIterator() {
        return clientList.iterator();
    }
}
