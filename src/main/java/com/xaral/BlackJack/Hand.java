package com.xaral.BlackJack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand implements Cloneable{
    private List<Card> cards;
    private boolean isSoft = false;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public Hand(Card[] cards) {
        this.cards = Arrays.stream(cards).toList();
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public Hand(Card card) {
        this.cards = new ArrayList<>();
        this.cards.add(card);
    }

    public int calculateScore() {
        int score = 0;
        if (this.cards.size() == 1 && this.cards.get(0).toString().equals("A"))
            return 11;
        for (Card card : this.cards) {
            score += card.getScore();
        }
        if (this.contains("A") && score < 12 && score != -1) {
            for (Card card : this.cards) {
                if (card.toString().equals("A")) {
                    card.setSoft(true);
                    break;
                }
            }
            return this.calculateScore();
        }
        if (this.contains("A") && score > 21 && this.isSoft()) {
            for (Card card : this.cards) {
                if (card.toString().equals("A")) {
                    card.setSoft(false);
                    break;
                }
            }
            return this.calculateScore();
        }
        this.isSoft = this.isSoft();
        if (score == 21 && this.cards.size() == 2)
            return -1;
        return score;
    }

    public boolean contains(String card) {
        for (Card handCard : this.cards) {
            if (card.equals(handCard.toString()))
                return true;
        }
        return false;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public boolean canSplit() {
        return cards.size() == 2 && cards.get(0).equals(cards.get(1));
    }

    public void split() {
        this.cards.remove(1);
    }

    public boolean isSoft() {
        if (!this.contains("A"))
            return false;
        for (Card card : this.cards) {
            if (card.toString().equals("A"))
                return card.isSoft();
        }
        return false;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public String toString() {
        return this.cards.toString();
    }

    public Hand clone() throws CloneNotSupportedException{
        Hand newHand = (Hand) super.clone();
        newHand.cards = new ArrayList<>(this.cards);
        return newHand;
    }
}
