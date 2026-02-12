package eu.merty.app.java.bj.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BJRulesetTest {

    @Test
    void getHandValueTreatsSingleAceAsElevenWhenSafe() {
        BJHand hand = newHand("As", "9d");
        assertEquals(20, BJRuleset.getHandValue(hand));
    }

    @Test
    void getHandValueHandlesMultipleAcesCorrectly() {
        BJHand hand = newHand("As", "Ad", "9c");
        assertEquals(21, BJRuleset.getHandValue(hand));
    }

    @Test
    void compareToDealerDetectsNaturalBlackjack() {
        BJHand player = newHand("As", "Kd");
        Hand dealer = newDealerHand("9c", "Th");
        assertEquals(2, BJRuleset.compareToDealer(player, dealer));
    }

    @Test
    void compareToDealerLosesAgainstDealerBlackjack() {
        BJHand player = newHand("9s", "Kd");
        Hand dealer = newDealerHand("Ac", "Th");
        assertEquals(-1, BJRuleset.compareToDealer(player, dealer));
    }

    @Test
    void compareToDealerPushesOnEqualBlackjack() {
        BJHand player = newHand("As", "Kd");
        Hand dealer = newDealerHand("Ac", "Th");
        assertEquals(0, BJRuleset.compareToDealer(player, dealer));
    }

    private BJHand newHand(String... cards) {
        BJHand hand = new BJHand(new Person("player"));
        for (String card : cards) {
            hand.addCard(toCard(card));
        }
        return hand;
    }

    private Hand newDealerHand(String... cards) {
        Hand hand = new Hand();
        for (String card : cards) {
            hand.addCard(toCard(card));
        }
        return hand;
    }

    private Card toCard(String card) {
        return new Card(card.charAt(1), card.charAt(0));
    }
}
