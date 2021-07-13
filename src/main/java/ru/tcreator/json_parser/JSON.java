package ru.tcreator.json_parser;

import com.google.gson.Gson;
import ru.tcreator.entity.Message;
import ru.tcreator.inerfaces.MessageEntityies;

public class JSON {
    protected static Gson gson = new Gson();
    /**
     * Сериализует объект в Json строку
     * @param msgObject {@link String}
     * @return строка json
     */
    static public String toJson(MessageEntityies msgObject) {
        return gson.toJson(msgObject);
    }

    /**
     * Парсит из JSON в Объект сообщения
     * @param json {@link String}
     * @return {@link MessageEntityies} объект сообщения
     */
    static public Message fromJsonMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}
