package com.xaral.BlackJack;

public class Card {
    private final String card;
    private Boolean isSoft = false;
    private Integer score = null;
    public Card(String card) {
        if (BlackJack.getDeck().contains(card.toUpperCase()))
            this.card = card.toUpperCase();
        else
            throw new RuntimeException("");
        this.score = this.calculateScore();
    }

    public Card(Integer card) {
        if (card < 2 || card > 11)
            throw new RuntimeException("");
        if (card == 11) {
            this.card = "A";
            return;
        }
        this.card = card.toString();
        this.score = this.calculateScore();
    }

    private Integer calculateScore() {
        switch (card) {
            case "A":
                if (isSoft)
                    score = 11;
                else
                    score = 1;
                break;
            case "2":
                score = 2;
                break;
            case "3":
                score = 3;
                break;
            case "4":
                score = 4;
                break;
            case "5":
                score = 5;
                break;
            case "6":
                score = 6;
                break;
            case "7":
                score = 7;
                break;
            case "8":
                score = 8;
                break;
            case "9":
                score = 9;
                break;
            case "10", "J", "Q", "K":
                score = 10;
                break;
        }
        return score;
    }

    public void setSoft(boolean isSoft) {
        this.isSoft = isSoft;
        if (card.equals("A"))
            this.score = this.calculateScore();
    }

    public boolean isSoft() {
        return this.isSoft;
    }

    public int getScore() {
        return this.score;
    }

    public boolean equals(Card card) {
        return this.card.equals(card.card);
    }

    public String toString() {
        return this.card;
    }
}
