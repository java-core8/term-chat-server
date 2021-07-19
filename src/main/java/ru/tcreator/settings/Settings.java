package ru.tcreator.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    protected static Properties properties;

    public Settings(String path) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(path));
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

}
