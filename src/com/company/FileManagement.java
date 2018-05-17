package com.company;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.nio.file.Paths.get;

class FileManagement {
    private Map<String, Integer> saveFile;
    private Path file;


    FileManagement() {
        file = get (System.getProperty ("user.home"), "IdeaProjects/BlackJack", "save.ser");
    }

    Map<String, Integer> readFile() {
        Properties properties = new Properties ();
        try {
            properties.load (new FileInputStream ("IdeaProjects/BlackJack/save.ser"));
        } catch (IOException e) {
            e.printStackTrace ();
        }
        for (String key : properties.stringPropertyNames ()) {
            saveFile.put(key, Integer.parseInt(properties.get(key).toString()));
        }
        return saveFile;
    }

    void writeFile(Map<String, Integer> saveFile) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream (new FileOutputStream ("IdeaProjects/BlackJack/save.ser"));
        } catch (IOException e) {
            e.printStackTrace ();
        }
        try {
            if (out != null) {
                out.writeObject(saveFile);
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }

        Properties properties = new Properties();
        properties.putAll(saveFile);
    }
}
