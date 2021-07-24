package ru.tcreator.clientmap;

import ru.tcreator.serv.ClientHandler;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientList {
    private final CopyOnWriteArrayList<ClientHandler> clientList;
    static private ClientList thisClientInstance;
    private ClientList() {
        clientList = new CopyOnWriteArrayList<>();
    }

    public static ClientList getInstance() {
        if(thisClientInstance == null) {
            thisClientInstance = new ClientList();
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
