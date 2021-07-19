package ru.tcreator.json_parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.tcreator.entity.Message;
import ru.tcreator.enums.Paths;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class JSON {
    protected static Gson gson = new Gson();
    /**
     * Сериализует объект в Json строку
     * @param msgObject {@link String}
     * @return строка json
     */
    static public String toJsonMessage(Message msgObject) {
        return gson.toJson(msgObject);
    }

    /**
     * Парсит из JSON в Объект сообщения
     * @param json {@link String}
     * @return {@link Message} объект сообщения
     */
    static public Message fromJsonMessage(String json) {
        return gson.fromJson(json, Message.class);
    }

    static public void addMessageFile(Message msg) {
        String path = Paths.JSON_LOG.getPath();
        List<Message> msgArr = readMessageSource();
        msgArr.add(msg);
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(msgArr, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public List<Message> readMessageSource() {
        String path = Paths.JSON_LOG.getPath();
        Gson gson = new Gson();
        List<Message> messageArray = null;
        try (Reader reader = new FileReader(path)) {
            Type messageTypeList = new TypeToken<List<Message>>(){}.getType();
            messageArray = gson.fromJson(reader, messageTypeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageArray;
    }
}
