package ru.tcreator.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReaderFile {
    protected File path;

    public ReaderFile(String path) {
        this.path = new File(path);
    }

    /**
     * Читает файл и возвращает строку
     * @return {@link String}
     */
    public String read() {
        StringBuilder stringBuilder = new StringBuilder();


        try(FileReader reader = new FileReader(path.getPath())) {
            int symbol;
            while((symbol=reader.read())!=-1) {
                stringBuilder.append((char) symbol);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return stringBuilder.toString();
    }

}
