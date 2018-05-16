package com.company;
import java.util.ArrayList;
import java.util.Random;
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
        System.out.println(d.deal());
//        while (money > 0) {
//
//        }
    }

    static class Deck {
        ArrayList<Card> cards = new ArrayList<Card>();
        int numDealt;

        Deck(int num) {
            for (int i = 1; i <= 52; i++) cards.add(new Card(i));
            shuffle();
            numDealt = 0;
        }

        public void shuffle() {
            int n = this.cards.size();
            Random random = new Random();
            for (int i = 0; i < this.cards.size(); i++) {
                int randomValue = i + random.nextInt(n - i);
                Card randomCard = this.cards.get(randomValue);
                this.cards.set(randomValue, this.cards.get(i));
                this.cards.set(i, randomCard);
            }
        }

        public Card deal() {
            Card dealCard = this.cards.remove(numDealt);
            numDealt = numDealt + 1;
            return dealCard;
        }
    }
    
    static class Hand {
        ArrayList<Card> hand = new ArrayList<Card>();

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
