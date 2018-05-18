package com.company;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

class Game {
    private User player;
    private User computer;
    private Deck d;
    private boolean playAgain;
    private Scanner reader;
    private int money;
    private Properties saveFile;
    private int numberOfDecks;

    Game() {
        this.player = new User ("Casey", 500);
        this.computer = new User ("Computer", 100);
        playAgain = true;
        money = 0;
        numberOfDecks = 0;
    }

    void run() throws IOException {
        reader = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = reader.nextLine();

        FileManagement file = new FileManagement();
        saveFile = file.readFile();
        System.out.println (saveFile);
        if (saveFile.containsKey(name)) {
            money = Integer.parseInt(saveFile.get(name).toString());
            System.out.println ("Welcome back, " + name + "! You currently have: $" + money);
        } else System.out.println ("Welcome " + name + ", how much money do you have?");

        while (money <= 0) {
            money = reader.nextInt();
            if (money <= 0) System.out.println("You've gotta have some dough, buddy...");
        }

        System.out.println("How many decks will you be playing with?");
        numberOfDecks = reader.nextInt();
        d = new Deck(numberOfDecks);

        while (player.getMoney() > 0 && playAgain) {
            player.setupBlackjack(d);
            computer.setupBlackjack(d);

            Bet b = new Bet(reader, player);
            int playerBet = b.bet();
            Hit hit = new Hit(computer, player, d, reader, playerBet);
            boolean playerWin = hit.playerHit();
            printPlayer();

            if (playerWin) {
                System.out.println("You won!! You have $" + player.getMoney() + " left.");
            } else {
                System.out.println("You lost. You have $" + player.getMoney() + " left");
            }

            if (player.getMoney() == 0) {
                System.out.println ("Gosh, " + player.getName() + ", I guess you're gonna have to come back when you have more dough...");
            } else {
                System.out.println ("Well, " + player.getName () + ", would you like to play again? Yes or No");
                String wantToPlay = reader.next ().toLowerCase ();
                playAgain = wantToPlay.equals ("y") || wantToPlay.equals ("yes");
                if (playAgain) {
                    resetGame ();
                }
            }
        }
        if (saveFile.containsKey (player.getName())) {
            saveFile.remove (player.getName());
        }

        saveFile.setProperty(player.getName(), Integer.toString(player.getMoney()));

        file.writeFile();
        reader.close();
    }

    private void printPlayer() {
        System.out.println("Your hand:");
        System.out.println(player.toString());
    }

    private void resetGame() {
        player.resetHand();
        computer.resetHand();
        if (d.getSize() < 15) {
            d = new Deck (numberOfDecks);
        }
    }
}
