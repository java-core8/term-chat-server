package ru.tcreator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.tcreator.entity.Message;
import ru.tcreator.serv.Server;

import java.io.IOException;


public class Start {
    public static void main(String[] args) throws IOException {
//        Gson gson = new GsonBuilder().create();
//        Message message = gson.fromJson(
//                ""
//                , Message.class);
//        System.out.println(message);
//        System.out.println(message.isNull());
        Server server = new Server();
        server.run();
    }
}

