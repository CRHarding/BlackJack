package com.company;

import java.util.ArrayList;

class Hand {
    private ArrayList<Card> hand = new ArrayList<>();
    private int size = 0;

    void addCard(Card newCard) {
        this.hand.add(newCard);
        size = size + 1;
    }

    void removeCard() {
        hand.remove(hand.size() - 1);
    }

    int getBlackjack() {
        int handScore = 0;
        for (Card card : this.hand) {
            if (card.getValue() == 1 && this.getTotal() + 10 == 21) {
                handScore = 50;
            }else if (card.getValue() == 1 && this.getTotal() + 10 <= 21 ) {
                handScore = handScore + 11;
            } else {
                handScore = handScore + card.getValue();
            }
        }
        System.out.println(handScore);
        return handScore;
    }

    int getTotal() {
        int value = 0;
        for (int i = 0; i < this.hand.size(); i++) {
            value = value + this.hand.get(i).getValue();
        }
        return value;
    }

    int getSize() {
        return this.size;
    }

    Card getCard(int place) {
        return this.hand.get(place);
    }
}
