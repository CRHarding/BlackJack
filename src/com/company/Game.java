package com.company;

import java.util.Scanner;

class Game {
    private User player;
    private User computer;
    private Deck d;
    private boolean playAgain;
    private Scanner reader;
    private int playerBet;
    private int numDecks;

    Game() {
        this.player = new User ("Casey", 500);
        this.computer = new User ("Computer", 100);
        playAgain = true;
        numDecks = 2;
        d = new Deck (numDecks);
        playerBet = 0;
    }

    void run() {
        reader = new Scanner(System.in);
//        System.out.println("What is your name?");
//        String name = reader.nextLine();
//        System.out.println("Welcome " + name + ", how much money do you have?");
//        int money = 0;
//        while (money <= 0) {
//            money = reader.nextInt();
//            if (money <= 0) System.out.println("You've gotta have some dough, buddy...");
//        }
//        System.out.println("How many decks will you be playing with?");
//        int numberOfDecks = reader.nextInt();
        while (player.getMoney() > 0 && playAgain) {
            player.setupBlackjack(d);
            computer.setupBlackjack(d);

            bet();
            printPlayer();
            hit();

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
        reader.close();
    }

    private void bet() {
        System.out.println(player.toString());
        System.out.println("You have $" + player.getMoney() + "...");
        System.out.println("How much would you like to bet, " + player.getName() + "?");
        do {
            playerBet = reader.nextInt();
            if (playerBet < 0) System.out.println ("You've gotta bet more than 0 ya dingus!");
            if (playerBet > player.getMoney())
                System.out.println ("We both know that you don't have that kinda dough...");
        } while (playerBet < 0 || playerBet > player.getMoney());
    }

    private void hit() {
        boolean hit = true;
        boolean playerWin = false;
        while (hit) {
            System.out.println("This is your total points --> " + player.getTotal());
            System.out.println("Would you like to hit or stay?");
            String choice = reader.next().toLowerCase();
            if (choice.equals("h") || choice.equals("hit")) {
                player.addCard(d);
                int playerTotal = player.getTotal();
                System.out.println("This is your total points --> " + playerTotal);
                if (playerTotal > 21 && playerTotal != 50) {
                    System.out.println("You busted, sorry chump.");
                    player.setMoney(playerBet * -1);
                    hit = false;
                    playerWin = false;
                } else {
                    System.out.println("This is your total points --> " + player.getTotal());
                    System.out.println ("Current Cards: " + player.toString ());
                }
            } else if (choice.equals("s") || choice.equals("stay")) {
                hit = false;
                playerWin = findWin();
            }
        }
        if (playerWin) {
            System.out.println("You won!! You have $" + player.getMoney() + " left.");
        } else {
            System.out.println("You lost. You have $" + player.getMoney() + " left");
        }
    }

    private boolean findWin() {
        System.out.println("Computer hand:");
        System.out.println(computer.toString());
        System.out.println ("Player total--->" + player.getTotal());
        System.out.println ("Computer total--->" + computer.getTotal());
        if (player.getTotal() == 50) {
            System.out.println ("YOU GOT BLACKJACK" + player.getName().toUpperCase() + "!!!");
            player.setMoney(playerBet * 4);
            return true;
        }else if (player.getTotal() > computer.getTotal()) {
            player.setMoney(playerBet * 2);
            return true;
        } else {
            player.setMoney(playerBet * -1);
            return false;
        }
    }

    private void printPlayer() {
        System.out.println("Your hand:");
        System.out.println(player.toString());
    }

    private void resetGame() {
        player.resetHand();
        computer.resetHand();
        playerBet = 0;
        if (d.getSize() < 15) {
            d = new Deck (numDecks);
        }
    }
}
