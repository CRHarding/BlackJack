package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Black Jack!");
        Game game = new Game();
        game.run();
    }
}
