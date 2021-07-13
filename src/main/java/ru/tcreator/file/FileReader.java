package ru.tcreator.file;

import java.io.File;

public class FileReader {
    protected File path;

    public FileReader(String path) {
        this.path = new File(path);
    }

    public void read() {

    }

}
