package com.company;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

import static java.nio.file.Paths.get;

class FileManagement {
    private Map<String, Integer> saveFile;
    private Path file;
    Properties properties;


    FileManagement() {
        file = get ("IdeaProjects/BlackJack", "save.ser");
        properties = new Properties();
    }

    Properties readFile() throws IOException {
        File file = new File("save.ser");
        if (file.exists()) {
            FileInputStream in = new FileInputStream ("save.ser");
            properties.load (in);
            in.close ();
            return properties;
        }
        else return properties;
    }

    void writeFile() throws IOException {
        FileOutputStream out = new FileOutputStream("save.ser");

        properties.store(out, "---Save File---");
    }
}
