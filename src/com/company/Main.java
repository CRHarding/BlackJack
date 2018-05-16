package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Welcome to Black Jack!");
//        Scanner reader = new Scanner(System.in);
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
        int numberOfDecks = 2;
        Deck d = new Deck(numberOfDecks);

//        while (money > 0) {
//
//        }
    }

    static class Deck {
        Card[] cards;

        Deck(int num) {
            cards = new Card[53];
            for (int i = 1; i <= 52; i++) cards[i] = new Card(i);
            for (int i = 1; i <= cards.length - 1; i++) {
                System.out.println(cards[i].toString());
            }
        }
    }

    static class Card {
        int rank;
        String suit;

        Card(int num) {
            rank = num % 13;
            int modulus = num % 4;
            switch (num % 4) {
                case 0:
                    suit = "Clubs";
                    break;
                case 1:
                    suit = "Diamonds";
                    break;
                case 2:
                    suit = "Hearts";
                    break;
                case 3:
                    suit = "Spades";
                    break;
                default:
                    System.out.println("Incorrect modulus for rank % 13 --> " + num % 4);
            }
        }

        public String toString() {
            if (this.rank != 0) return this.rank + " of " + this.suit;
            return "Ace of " + this.suit;
        }
    }
}
