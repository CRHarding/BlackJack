package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Path file = Paths.get(System.getProperty("user.home"), "IdeaProjects/BlackJack", "save.txt");
        HashMap<String, Integer> saveFile = new HashMap<>();
        boolean isRegularExecutableFile = Files.isRegularFile (file) & Files.isReadable(file) & Files.isExecutable (file);
        if (isRegularExecutableFile) {
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedReader reader = Files.newBufferedReader (file, charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == ' ') {
                            String name = line.substring(0, i-1);
                            Integer score = Integer.parseInt(line.substring(i + 1));
                            saveFile.put(name, score);
                        }
                    }
                }
            } catch (IOException x) {
                System.err.format("IOException %s%n", x);
            }
        }
        System.out.println("Welcome to Black Jack!");
        Game game = new Game(saveFile);
        game.run();
    }
}
