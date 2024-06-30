package com.xaral.BlackJack;

import java.util.HashMap;
import java.util.Map;

public class Probability {
    private final Hand playerHand;

    private final boolean takeSoft;

    private final Card dealerCard;

    public Probability(Hand playerHand, Card dealerCard) {
        this.playerHand = playerHand;
        this.dealerCard = dealerCard;
        this.takeSoft = false;
    }

    public Probability(Hand playerHand, Card dealerCard, boolean takeSoft) {
        this.playerHand = playerHand;
        this.dealerCard = dealerCard;
        this.takeSoft = takeSoft;
    }

    private Map<String, Double> calculateDealerCombinations() {
        Map<String, Double> combinations = new HashMap<>();
        combinations.put("combinations_17", 0.0);
        combinations.put("combinations_18", 0.0);
        combinations.put("combinations_19", 0.0);
        combinations.put("combinations_20", 0.0);
        combinations.put("combinations_21", 0.0);
        combinations.put("combinations_blackjack", 0.0);
        combinations.put("combinations_overkill", 0.0);
        for (String card : BlackJack.getDeck()) {
            Hand newHand = new Hand(dealerCard);
            newHand.addCard(new Card(card));
            int score = newHand.calculateScore();
            if (score < 17 && score != -1)
                combinations = calculateDealerCombinations(newHand, combinations);
            else if (score == 17)
                combinations.put("combinations_17", combinations.get("combinations_17") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 18)
                combinations.put("combinations_18", combinations.get("combinations_18") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 19)
                combinations.put("combinations_19", combinations.get("combinations_19") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 20)
                combinations.put("combinations_20", combinations.get("combinations_20") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 21)
                combinations.put("combinations_21", combinations.get("combinations_21") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == -1)
                combinations.put("combinations_blackjack", combinations.get("combinations_blackjack") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else
                combinations.put("combinations_overkill", combinations.get("combinations_overkill") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
        }
        return combinations;
    }

    private Map<String, Double> calculateDealerCombinations(Hand hand, Map<String, Double> combinations) {
        for (String card : BlackJack.getDeck()) {
            Hand newHand = null;
            try {
                newHand = hand.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            newHand.addCard(new Card(card));
            int score = newHand.calculateScore();
            if ((score < 17 && score != -1) || (score == 17 && newHand.contains("A") && newHand.isSoft() && takeSoft))
                combinations = calculateDealerCombinations(newHand, combinations);
            else if (score == 17)
                combinations.put("combinations_17", combinations.get("combinations_17") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 18)
                combinations.put("combinations_18", combinations.get("combinations_18") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 19)
                combinations.put("combinations_19", combinations.get("combinations_19") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 20)
                combinations.put("combinations_20", combinations.get("combinations_20") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == 21)
                combinations.put("combinations_21", combinations.get("combinations_21") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else if (score == -1)
                combinations.put("combinations_blackjack", combinations.get("combinations_blackjack") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
            else
                combinations.put("combinations_overkill", combinations.get("combinations_overkill") + Math.pow((double) 1 / BlackJack.getDeck().size(), newHand.getCards().size() - 1));
        }
        return combinations;
    }

    public Map<String, Double> probabilityStand() {
        double winProbability = 0;
        double drawProbability = 0;
        double loseProbability = 0;
        Map<String, Double> dealerProbability = this.calculateDealerCombinations();
        int playerScore = playerHand.calculateScore();
        if (playerScore < 17 && playerScore != -1)
            winProbability += dealerProbability.get("combinations_overkill");
        else if (playerScore == 17) {
            winProbability += dealerProbability.get("combinations_overkill");
            drawProbability += dealerProbability.get("combinations_17");
        }
        else if (playerScore == 18) {
            winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17");
            drawProbability += dealerProbability.get("combinations_18");
        }
        else if (playerScore == 19) {
            winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18");
            drawProbability += dealerProbability.get("combinations_19");
        }
        else if (playerScore == 20) {
            winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19");
            drawProbability += dealerProbability.get("combinations_20");
        }
        else if (playerScore == 21) {
            winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19") + dealerProbability.get("combinations_20");
            drawProbability += dealerProbability.get("combinations_21");
        }
        else if (playerScore == -1) {
            winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19") + dealerProbability.get("combinations_20") + dealerProbability.get("combinations_21");
            drawProbability += dealerProbability.get("combinations_blackjack");
        }
        loseProbability = 1 - winProbability - drawProbability;
        Map<String, Double> results = new HashMap<>();
        results.put("win_probability", winProbability);
        results.put("draw_probability", drawProbability);
        results.put("lose_probability", loseProbability);
        results.put("expected_value", winProbability - loseProbability);
        return results;
    }

    public Map<String, Double> probabilityDouble() {
        double winProbability = 0;
        double drawProbability = 0;
        double loseProbability = 0;
        Map<String, Double> dealerProbability = this.calculateDealerCombinations();
        for (String cardString : BlackJack.getDeck()) {
            Hand hand = null;
            try {
                hand = playerHand.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            hand.addCard(new Card(cardString));
            int playerScore = hand.calculateScore();
            if (playerScore < 17 && playerScore != -1)
                winProbability += dealerProbability.get("combinations_overkill");
            else if (playerScore == 17) {
                winProbability += dealerProbability.get("combinations_overkill");
                drawProbability += dealerProbability.get("combinations_17");
            }
            else if (playerScore == 18) {
                winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17");
                drawProbability += dealerProbability.get("combinations_18");
            }
            else if (playerScore == 19) {
                winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18");
                drawProbability += dealerProbability.get("combinations_19");
            }
            else if (playerScore == 20) {
                winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19");
                drawProbability += dealerProbability.get("combinations_20");
            }
            else if (playerScore == 21) {
                winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19") + dealerProbability.get("combinations_20");
                drawProbability += dealerProbability.get("combinations_21");
            }
            else if (playerScore == -1) {
                winProbability += dealerProbability.get("combinations_overkill") + dealerProbability.get("combinations_17") + dealerProbability.get("combinations_18") + dealerProbability.get("combinations_19") + dealerProbability.get("combinations_20") + dealerProbability.get("combinations_21");
                drawProbability += dealerProbability.get("combinations_blackjack");
            }
        }
        winProbability /= BlackJack.getDeck().size();
        drawProbability /= BlackJack.getDeck().size();
        loseProbability = 1 - winProbability - drawProbability;
        Map<String, Double> results = new HashMap<>();
        results.put("win_probability", winProbability);
        results.put("draw_probability", drawProbability);
        results.put("lose_probability", loseProbability);
        results.put("expected_value", 2 * (winProbability - loseProbability));
        return results;
    }

    public Map<String, Double> probabilityHit() {
        double winProbability = 0;
        double drawProbability = 0;
        double loseProbability = 0;
        double expectedValue = 0;
        for (String cardString : BlackJack.getDeck()) {
            Hand newHand = null;
            try {
                newHand = playerHand.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            newHand.addCard(new Card(cardString));
            int score = newHand.calculateScore();
            if (score > 21)
                continue;
            Probability probability = new Probability(newHand, dealerCard, takeSoft);
            Map<String, Double> probabilityStand = probability.probabilityStand();
            Map<String, Double> probabilityDouble = probability.probabilityDouble();
            Double expStand = probabilityStand.get("expected_value");
            Double expDouble = probabilityDouble.get("expected_value") / 2;
            if ((Math.abs(expStand - expDouble) <= 0.000001 || expDouble >= expStand) && expDouble <= 0) {
                Map<String, Double> probabilityHit = this.probabilityHit(newHand, 1);
                winProbability += probabilityHit.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
                drawProbability += probabilityHit.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
                expectedValue += probabilityHit.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
            }
            else if (expDouble > 0) {
                winProbability += probabilityDouble.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
                drawProbability += probabilityDouble.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
                expectedValue += probabilityDouble.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
            }
            else {
                winProbability += probabilityStand.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
                drawProbability += probabilityStand.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
                expectedValue += probabilityStand.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);
            }
        }
        loseProbability = 1 - winProbability - drawProbability;
        Map<String, Double> results = new HashMap<>();
        results.put("win_probability", winProbability);
        results.put("draw_probability", drawProbability);
        results.put("lose_probability", loseProbability);
        results.put("expected_value", 2 * (winProbability - loseProbability));
        return results;
    }

    private Map<String, Double> probabilityHit(Hand hand, Integer i) {
        double winProbability = 0;
        double drawProbability = 0;
        double loseProbability = 0;
        double expectedValue = 0;
        for (String cardString : BlackJack.getDeck()) {
            Hand newHand = null;
            try {
                newHand = hand.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            newHand.addCard(new Card(cardString));
            int score = newHand.calculateScore();
            if (score > 21)
                continue;
            Probability probability = new Probability(newHand, dealerCard, takeSoft);
            Map<String, Double> probabilityStand = probability.probabilityStand();
            Map<String, Double> probabilityDouble = probability.probabilityDouble();
            Double expStand = probabilityStand.get("expected_value");
            Double expDouble = probabilityDouble.get("expected_value") / 2;
            if ((Math.abs(expStand - expDouble) <= 0.000001 || expDouble >= expStand) && expDouble <= 0) {
                Map<String, Double> probabilityHit = this.probabilityHit(newHand, i + 1);
                winProbability += probabilityHit.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
                drawProbability += probabilityHit.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
                expectedValue += probabilityHit.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), 1);;
            }
            else if (expDouble > 0) {
                winProbability += probabilityDouble.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
                drawProbability += probabilityDouble.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
                expectedValue += probabilityDouble.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
            }
            else {
                winProbability += probabilityStand.get("win_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
                drawProbability += probabilityStand.get("draw_probability") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
                expectedValue += probabilityStand.get("expected_value") * Math.pow((double) 1 / BlackJack.getDeck().size(), i);
            }
        }
        loseProbability = 1 - winProbability - drawProbability;
        Map<String, Double> results = new HashMap<>();
        results.put("win_probability", winProbability);
        results.put("draw_probability", drawProbability);
        results.put("lose_probability", loseProbability);
        results.put("expected_value", 2 * (winProbability - loseProbability));
        return results;
    }

    public Map<String, Double> probabilitySplit() {
        double winProbability = 0;
        double drawProbability = 0;
        double loseProbability = 0;
        double expectedValue = 0;
        for (String cardString : BlackJack.getDeck()) {
            Hand hand = new Hand(playerHand.getCards().get(0));
            hand.addCard(new Card(cardString));
            Probability probability = new Probability(hand, dealerCard, takeSoft);
            Map<String, Double> probabilityStand = probability.probabilityStand();
            Map<String, Double> probabilityDouble = probability.probabilityDouble();
            Map<String, Double> probabilityHit = probability.probabilityHit();
            Double expStand = probabilityStand.get("expected_value");
            Double expHit = probabilityStand.get("expected_value");
            Double expDouble = probabilityStand.get("expected_value");
            if (expStand >= expHit && expStand >= expDouble) {
                winProbability += probabilityStand.get("win_probability") / BlackJack.getDeck().size();
                drawProbability += probabilityStand.get("draw_probability") / BlackJack.getDeck().size();
                expectedValue += expStand / BlackJack.getDeck().size();
            } else if (expHit >= expStand && expHit >= expDouble) {
                winProbability += probabilityHit.get("win_probability") / BlackJack.getDeck().size();
                drawProbability += probabilityHit.get("draw_probability") / BlackJack.getDeck().size();
                expectedValue += expHit / BlackJack.getDeck().size();
            } else {
                winProbability += probabilityDouble.get("win_probability") / BlackJack.getDeck().size();
                drawProbability += probabilityDouble.get("draw_probability") / BlackJack.getDeck().size();
                expectedValue += expDouble / BlackJack.getDeck().size();
            }
        }
        loseProbability = 1 - winProbability - drawProbability;
        expectedValue *= 2;
        Map<String, Double> results = new HashMap<>();
        results.put("win_probability", Math.pow(winProbability, 2));
        results.put("draw_probability", 1 - Math.pow(winProbability, 2) - Math.pow(loseProbability, 2));
        results.put("lose_probability", Math.pow(loseProbability, 2));
        results.put("expected_value", expectedValue);
        return results;
    }
}