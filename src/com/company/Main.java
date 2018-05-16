package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Black Jack!");
        Scanner reader = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = reader.nextLine();
        System.out.println("Welcome " + name + ", how much money do you have?");
        int money = 0;
        while (money <= 0) {
            money = reader.nextInt();
            if (money <= 0) System.out.println("You've gotta have some dough, buddy...");
        }
        System.out.println("How many decks will you be playing with?");
        int numberOfDecks = reader.nextInt();

        Deck d = new Deck(numberOfDecks);

        while (money > 0) {

        }
    }

    static class Deck {
        Card[] cards;

        Deck(int num) { cards = new Card[52 * num]; }
    }

    static class Card {
        
    }
}
