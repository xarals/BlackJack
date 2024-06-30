package com.xaral.BlackJack;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BlackJack {
    private static final List<String> deck = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
    private static final List<String> suit = Arrays.asList("♠", "♥", "♦", "♣");
    private Hand playerHand;
    private boolean playerHandStand = false;
    private Hand playerHandSplit;
    private boolean playerHandSplitStand = false;
    private Hand dealerHand;
    private boolean takeSoft;
    private int bet;
    private int splitBet;

    public BlackJack() {
        this.playerHand = new Hand();
        this.playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.dealerHand = new Hand();
        this.dealerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.bet = 10;
        this.takeSoft = false;
        this.splitBet = bet;
    }

    public BlackJack(int bet) {
        this.playerHand = new Hand();
        this.playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.dealerHand = new Hand();
        this.dealerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        this.bet = bet;
        this.takeSoft = false;
        this.splitBet = bet;
    }

    public BlackJack(Hand playerHand, Card dealerCard, int bet) {
        this.playerHand = playerHand;
        this.dealerHand = new Hand(dealerCard);
        this.bet = bet;
        this.takeSoft = false;
        this.splitBet = bet;
    }

    public BlackJack(Hand playerHand, Card dealerCard, int bet, boolean takeSoft) {
        this.playerHand = playerHand;
        this.dealerHand = new Hand(dealerCard);
        this.bet = bet;
        this.takeSoft = takeSoft;
        this.splitBet = bet;
    }

    private Hand dealerTakeCards() {
        int score = dealerHand.calculateScore();
        while (score < 17 || (score == 17 && takeSoft)) {
            dealerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
            score = dealerHand.calculateScore();
        }
        return dealerHand;
    }

    private void playerHit(boolean isMainHand) {
        if (isMainHand)
            playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        else
            playerHandSplit.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
    }

    private void playerDouble(boolean isMainHand) {
        if (isMainHand) {
            playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
            bet *= 2;
        }
        else {
            playerHandSplit.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
            splitBet *= 2;
        }
    }

    private void playerSplit() {
        playerHand.split();
        try {
            playerHandSplit = playerHand.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        playerHand.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
        playerHandSplit.addCard(new Card(deck.get((int) (Math.random() * deck.size()))));
    }

    public static double playComputer() {
        double expectedValue = 0.0;
        int numberHands = 0;
        for (String playerCard1String : deck) {
            for (String playerCard2String : deck) {
                for (String dealerCardString : deck) {
                    if (numberHands != 0)
                        System.out.println(numberHands + ". " + (double) expectedValue / numberHands);
                    Hand playerHand = new Hand(new Card[]{new Card(playerCard1String), new Card(playerCard2String)});
                    if (playerHand.calculateScore() == -1 && !dealerCardString.equals("A")) {
                        expectedValue += 1.5;
                        numberHands++;
                        continue;
                    }
                    Probability probability = new Probability(playerHand, new Card(dealerCardString));
                    Double expStand = probability.probabilityStand().get("expected_value");
                    Double expHit = probability.probabilityHit().get("expected_value");
                    Double expDouble = probability.probabilityDouble().get("expected_value");
                    Double expSplit = -10.0;
                    if (playerHand.canSplit())
                        expSplit = probability.probabilitySplit().get("expected_value");
                    expectedValue += Math.max(Math.max(Math.max(expStand, expHit), expDouble), expSplit);
                    numberHands++;
                }
            }
        }
        return expectedValue / numberHands;
    }

    public int playPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Moves:");
        System.out.println("1 - Stand");
        System.out.println("2 - Hit");
        System.out.println("3 - Double");
        System.out.println("4 - Split\n");
        System.out.println("If you have 2 hands (after a split), then enter the values separated by a space " +
                "\n(if one of the hands has already lost, then write any number)");
        System.out.println();
        while (true) {
            System.out.println("\nDealer cards: " + dealerHand.getCards().get(0) + suit.get((int) (Math.random() * suit.size())));
            System.out.print("Your cards: ");
            for (Card card : playerHand.getCards())
                System.out.print(card.toString() + suit.get((int) (Math.random() * suit.size())) + " ");
            System.out.print("\n");
            if (playerHandSplit != null) {
                System.out.print("            ");
                for (Card card : playerHandSplit.getCards())
                    System.out.print(card.toString() + suit.get((int) (Math.random() * suit.size())) + " ");
                System.out.print("\n");
            }
            String[] moves;
            String mainMove;
            String splitMove = null;
            if (playerHandSplit != null) {
                System.out.print("Your moves: ");
                moves = scanner.nextLine().split(" ");
                try {
                    mainMove = moves[0];
                    splitMove = moves[1];
                } catch (Exception e) {
                    System.out.println("\nError!\n");
                    continue;
                }
            }
            else {
                System.out.print("Your move: ");
                moves = scanner.nextLine().split(" ");
                try {
                    mainMove = moves[0];
                } catch (Exception e) {
                    System.out.println("\nError!\n");
                    continue;
                }
            }
            if (playerHand.calculateScore() < 21 && !playerHandStand && playerHand.calculateScore() != -1) {
                if (mainMove.equals("1"))
                    playerHandStand = true;
                else if (mainMove.equals("2"))
                    this.playerHit(true);
                else if (mainMove.equals("3")) {
                    this.playerDouble(true);
                    playerHandStand = true;
                }
                else if (mainMove.equals("4") && playerHandSplit == null && playerHand.canSplit()) {
                    this.playerSplit();
                    continue;
                }
                else
                    System.out.println("\nError!\n");
            }
            if (playerHandSplit != null && playerHandSplit.calculateScore() < 21 && !playerHandSplitStand) {
                if (splitMove.equals("1"))
                    playerHandSplitStand = true;
                else if (splitMove.equals("2"))
                    this.playerHit(false);
                else if (splitMove.equals("3")) {
                    this.playerDouble(false);
                    playerHandSplitStand = true;
                }
                else
                    System.out.println("\nError!\n");
            }
            if (playerHand.calculateScore() == -1 || playerHand.calculateScore() >= 21)
                playerHandStand = true;
            if (playerHandSplit != null && (playerHandSplit.calculateScore() == -1 || playerHandSplit.calculateScore() >= 21))
                playerHandSplitStand = true;
            if ((playerHandStand && playerHandSplit == null) || (playerHandStand && playerHandSplitStand)) {
                break;
            }
        }
        int bank = 0;
        dealerTakeCards();
        int dealerScore = dealerHand.calculateScore();
        int playerScore = playerHand.calculateScore();
        Integer playerSplitScore = null;
        if (playerHandSplit != null)
            playerSplitScore = playerHandSplit.calculateScore();
        if (playerScore > 21 || (dealerScore <= 21 && dealerScore > playerScore && playerScore != -1) || (dealerScore == -1 && playerScore != -1))
            bank -= bet;
        else if (playerScore == dealerScore) {}
        else
            bank += bet;
        if (playerSplitScore != null) {
            if (playerSplitScore > 21 || (dealerScore <= 21 && dealerScore > playerSplitScore && playerSplitScore != -1) || (dealerScore == -1 && playerSplitScore != -1))
                bank -= splitBet;
            else if (playerSplitScore == dealerScore) {
            } else
                bank += splitBet;
        }
        System.out.print("\nDealer cards: ");
        for (Card card : dealerHand.getCards())
            System.out.print(card.toString() + suit.get((int) (Math.random() * suit.size())) + " ");
        System.out.print("\n");
        System.out.print("Your cards: ");
        for (Card card : playerHand.getCards())
            System.out.print(card.toString() + suit.get((int) (Math.random() * suit.size())) + " ");
        System.out.print("\n");
        if (playerHandSplit != null) {
            System.out.print("            ");
            for (Card card : playerHandSplit.getCards())
                System.out.print(card.toString() + suit.get((int) (Math.random() * suit.size())) + " ");
            System.out.print("\n");
        }
        if (bank > 0)
            System.out.println("You won " + bank + "!");
        else if (bank < 0)
            System.out.println("You lost " + -bank + "!");
        else
            System.out.println("Draw!");
        return bank;
    }

    public static List<String> getDeck() {
        return deck;
    }

    public Hand getPlayerHand() {
        return this.playerHand;
    }

    public Hand getPlayerHandSplit() {
        return this.playerHandSplit;
    }

    public Hand getDealerHand() {
        return this.dealerHand;
    }

    public boolean getTakeSoft() {
        return this.takeSoft;
    }

    public void setTakeSoft(boolean takeSoft) {
        this.takeSoft = takeSoft;
    }
}
