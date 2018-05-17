package com.company;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Black Jack!");
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
        int money = 50;
        boolean playAgain = true;

        Deck d = new Deck(numberOfDecks);

        Game game = new Game();
        game.run();
    }

    static class Game {
        User player;
        User computer;
        Deck d;
        boolean playAgain;
        Scanner reader;
        int playerBet;
        int numDecks;

        Game() {
            this.player = new User("Casey", 500);
            this.computer = new User("Computer", 100);
            playAgain = true;
            numDecks = 2;
            d = new Deck(numDecks);
            playerBet = 0;
        }

        private void run() {
            reader = new Scanner(System.in);
            while (player.getMoney() > 0 && playAgain) {
                player.setupBlackjack(d);
                computer.setupBlackjack(d);
                bet();
                player.setMoney(playerBet);

                printPlayer();
                hit();

                System.out.println("Would you like to play again? Yes or No");
                String wantToPlay = reader.nextLine().toLowerCase();
                reader.next();
                playAgain = wantToPlay.equals("y") || wantToPlay.equals("yes");
                if (playAgain) {
                    resetGame();
                    player.setupBlackjack(d);
                    computer.setupBlackjack(d);
                }
            }
            reader.close();
        }

        private void bet() {
            System.out.println(player.toString());
            System.out.println("You have $" + player.getMoney() + "...");
            System.out.println("How much would you like to bet?");
            do {
                int playerBet = reader.nextInt();
                if (playerBet < 0) System.out.println ("You've gotta bet more than 0 ya dingus!");
                if (playerBet > player.getMoney())
                    System.out.println ("We both know that you don't have that kinda dough...");
            } while (playerBet < 0 || playerBet > player.getMoney());
        }

        private void hit() {
            boolean hit = true;
            boolean playerWin = false;
            while (hit) {
                System.out.println("Would you like to hit or stay?");
                String choice = reader.next().toLowerCase();
                if (choice.equals("h") || choice.equals("hit")) {
                    player.addCard(d);
                    int playerTotal = player.getBlackjack();
                    System.out.println("This is your total points --> " + playerTotal);
                    if (playerTotal > 21 && playerTotal != 50) {
                        System.out.println("You busted, sorry chump.");
                        hit = false;
                    } else {
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
                System.out.println("Sorry, chump. You lost. You have $" + player.getMoney() + " left");
            }
        }

        private boolean findWin() {
            System.out.println("Computer hand:");
            System.out.println(computer.toString());
            System.out.println ("Player total--->" + player.getTotal());
            System.out.println ("Computer total--->" + computer.getTotal());
            if (player.getTotal() == 50) {
                System.out.println ("YOU GOT BLACKJACK!!!");
                player.setMoney(player.getMoney() + playerBet * 4);
                return true;
            }else if (player.getTotal() > computer.getTotal()) {
                player.setMoney(player.getMoney() + playerBet * 2);
                return true;
            } else {
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
                d = new Deck(numDecks);
            }
        }
    }

    static class User {
        String name;
        Hand hand;
        int money;

        User(String name, int money) {
            this.name = name;
            this.money = money;
            hand = new Hand();
        }

        private int getMoney() {
            return this.money;
        }

        private void setMoney(int amount) {
            this.money = this.money - amount;
        }

        private void addCard(Deck d) { hand.addCard(d.deal()); }

        private void setupBlackjack(Deck d) {
            hand.addCard(d.deal());
            hand.addCard(d.deal());
        }

        private void resetHand() {
            for (int i = 0; i < hand.getSize(); i++) {
                hand.removeCard();
            }
        }

        private int getSize() {
            return hand.getSize();
        }

        public String toString() {
            String returnString = "";
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

        private int getBlackjack() {
            return this.hand.getBlackjack();
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

        private int getSize() {
            return cards.size();
        }
    }

    static class Hand {
        ArrayList<Card> hand = new ArrayList<>();
        int size = 0;

        private void addCard(Card newCard) {
            this.hand.add(newCard);
            size = size + 1;
        }

        private void removeCard() {
            hand.remove(hand.size() - 1);
        }

        private int getBlackjack() {
            int handScore = 0;
            for (Card card : this.hand) {
                if (card.score == 1 && this.getTotal() + 10 == 21) {
                    handScore = 50;
                }else if (card.score == 1 && this.getTotal() + 10 <= 21 ) {
                    handScore = handScore + 11;
                } else {
                    handScore = handScore + card.getValue();
                }
            }
            System.out.println(handScore);
            return handScore;
        }

        private int getTotal() {
            int value = 0;
            for (int i = 0; i < this.hand.size(); i++) {
                value = value + this.hand.get(i).getValue();
            }
            return value;
        }

        private int getSize() {
            return this.size;
        }

        private Card getCard(int place) {
            return this.hand.get(place);
        }
    }

    static class Card {
        int rank;
        int score;
        String suit;

        Card(int num) {
            rank = num % 13;
            score = rank;
            if (rank == 0) {
                rank = 1;
                score = 1;
            }
            if (score > 10) {
                score = 10;
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
            return this.score;
        }
    }
}
