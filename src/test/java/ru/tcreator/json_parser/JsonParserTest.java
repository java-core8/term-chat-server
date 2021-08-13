package ru.tcreator.json_parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.tcreator.entity.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class JsonParserTest {
    @Test
    @DisplayName("Тест парсера на конвертирование JSON в строку")
    @Tag("JSON")
    void testJsonParsingToString() {
        Pattern pattern = Pattern.compile("testMsg");
        Message testMessage = new Message("test", "testMsg");
        String jsonString = JSON.toJsonMessage(testMessage);
        Matcher matcher = pattern.matcher(jsonString);
        assertTrue(matcher.find());
    }
    @Test
    @DisplayName("Тест парсера JSON на выброс исключения")
    @Tag("JSON")
    void testJsonParsingToStringAndThrowing() {
        Message messageMock = mock(Message.class);
        assertThrows( UnsupportedOperationException.class, () -> JSON.toJsonMessage(messageMock));
    }

    @Test
    @DisplayName("Тест парсера JSON, без исключений ")
    @Tag("JSON")
    void testJsonParsingToStringAndNotThrowing() {
        assertDoesNotThrow(() -> JSON.toJsonMessage(
                new Message("test", "test")
        ));
    }


    @Test
    @DisplayName("Тест парсера JSON, преобразование в объект сообщения")
    @Tag("JSON")
    void testJsonParsingToMessageFromString() {
        String testJsonString = "{\"msg\":\"test\",\"time\":\"06:02:02 PM\",\"timeStamp\":1628521322372,\"formatTime\":\"hh:mm:ss a\",\"from\":\"testMsg\",\"to\":\"all\",\"isCommand\":false}";
        String equalStr = "[all] testMsg 06:02:02 PM >>> test";
        Message fromJson = JSON.fromJsonMessage(testJsonString);
        assertEquals(equalStr, fromJson.toString());
    }

    @Test
    @DisplayName("Тест парсера JSON, из Message  строку, без исключения ")
    @Tag("JSON")
    void testJsonParsingToMessageFromStringAndNotThrowing() {
        String testJsonString = "{\"msg\":\"test\",\"time\":\"06:02:02 PM\",\"timeStamp\":1628521322372,\"formatTime\":\"hh:mm:ss a\",\"from\":\"testMsg\",\"to\":\"all\",\"isCommand\":false}";
        assertDoesNotThrow(() -> JSON.toJsonMessage(
                JSON.fromJsonMessage(testJsonString)
        ));
    }
}
