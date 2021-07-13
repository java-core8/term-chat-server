package ru.tcreator.file;

import java.io.File;

public class FileWriter {
    protected File path;

    public FileWriter(String path) {
        this.path = new File(path);
    }

    public void write() {

    }
}
