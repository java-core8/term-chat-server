package ru.tcreator.settings;

import ru.tcreator.enums.Paths;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Settings {
    protected static Properties properties;

    public Settings(File path) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(path.getPath()));
    }

    public Settings(String path) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(path));
    }

    public void setProperties(String key, String value) {
        properties.put(key, value);
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

}
