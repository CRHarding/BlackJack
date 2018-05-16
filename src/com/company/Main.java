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
        User player = new User("Casey", d);
        User computer = new User("Computer", d);
        player.setupBlackjack();
        computer.setupBlackjack();

        System.out.println(player);
        System.out.println(computer);

        if (player.getTotal() > computer.getTotal()) {
            System.out.println("You won!!");
        } else {
            System.out.println("Sorry, chump. You lost.");
        }
    }

    static class User {
        String name;
        Deck d;
        Hand hand;

        User(String name, Deck d) {
            this.name = name;
            this.d = d;
            hand = new Hand();
        }

        private void addCard() {
            hand.addCard(d.deal());
        }

        private void setupBlackjack() {
            hand.addCard(d.deal());
            hand.addCard(d.deal());
        }

        private int getSize() {
            return hand.getSize();
        }

        public String toString() {
            String returnString = "";
            if (this.hand.getCard(0).toString().contains("Ace")) {
                returnString = "You have an ";
            } else {
                returnString = "You have a ";
            }

            for (int i = 0; i < this.hand.getSize(); i++) {
                if (i != this.hand.getSize() - 1) {
                    if (this.hand.getCard(i + 1).toString().contains("Ace")) {
                        returnString = returnString + this.hand.getCard(i) + " and an ";
                    } else {
                        returnString = returnString + this.hand.getCard(i) + " and a ";
                    }
                } else {
                    returnString = returnString + this.hand.getCard(i);
                }
            }
            return returnString;
        }

        private int getTotal() {
            return this.hand.getTotal();
        }
    }

    static class Deck {
        ArrayList<Card> cards = new ArrayList<>();
        int numDealt;

        Deck(int num) {
            for (int i = 1; i <= 52; i++) {
                cards.add(new Card(i));
            }
            shuffle();
            numDealt = 0;
        }

        private void shuffle() {
            int n = this.cards.size();
            Random random = new Random();
            for (int i = 0; i < this.cards.size(); i++) {
                int randomValue = i + random.nextInt(n - i);
                Card randomCard = this.cards.get(randomValue);
                this.cards.set(randomValue, this.cards.get(i));
                this.cards.set(i, randomCard);
            }
        }

        private Card deal() {
            Card dealCard = this.cards.remove(numDealt);
            numDealt = numDealt + 1;
            return dealCard;
        }
    }

    static class Hand {
        ArrayList<Card> hand = new ArrayList<Card>();
        int size = 0;

        private void addCard(Card newCard) {
            this.hand.add(newCard);
            size = size + 1;
        }

        public int getTotal() {
            int value = 0;
            for (int i = 0; i < hand.size(); i++) {
                Card valueCard = hand.get(i);
                value = value + valueCard.getValue();
            }
            return value;
        }

        public int getSize() {
            return this.size;
        }

        public Card getCard(int place) {
            return this.hand.get(place);
        }
    }

    static class Card {
        int rank;
        String suit;

        Card(int num) {
            rank = num % 13;

            if (rank == 0) {
                rank = 11;
            }
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
            if (this.rank != 1 && this.rank < 11) return this.rank + " of " + this.suit;
            if (this.rank == 1) return "Ace of " + this.suit;
            switch (this.rank) {
                case 11:
                    return "Jack of " + this.suit;
                case 12:
                    return "Queen of " + this.suit;
                case 13:
                    return "King of " + this.suit;
                default:
                    return "Incorrect input for this.rank in card toString method";
            }
        }

        private int getValue() {
            return this.rank;
        }
    }
}
