package ru.tcreator.clientmap;

import ru.tcreator.serv.ClientHandler;

import java.util.concurrent.ConcurrentHashMap;

public class ClientMap {
    private ConcurrentHashMap<String, ClientHandler> clientMap;
    static private ClientMap thisClientInstance;
    private ClientMap() {
        clientMap = new ConcurrentHashMap<>();
    }

    public static ClientMap getInstance() {
        if(thisClientInstance == null) {
            thisClientInstance = new ClientMap();
        }
        return thisClientInstance;
    }

    public void add(String key, ClientHandler value) {
        clientMap.putIfAbsent(key, value);
    }

    public boolean remove(String key, ClientHandler value) {
        return clientMap.remove(key, value);
    }
}
