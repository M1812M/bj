package eu.merty.app.java.bj.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BJRuleset {
    private static final Map<Character, Integer> CARD_VALUES;

    static {
        Map<Character, Integer> cardValues = new HashMap<>();
        cardValues.put('2', 2);
        cardValues.put('3', 3);
        cardValues.put('4', 4);
        cardValues.put('5', 5);
        cardValues.put('6', 6);
        cardValues.put('7', 7);
        cardValues.put('8', 8);
        cardValues.put('9', 9);
        cardValues.put('T', 10);
        cardValues.put('J', 10);
        cardValues.put('Q', 10);
        cardValues.put('K', 10);
        cardValues.put('A', 1);
        CARD_VALUES = Collections.unmodifiableMap(cardValues);
    }

    public static boolean maySplit(BJHand h) {
        return mayHitAndStand(h)
                && h.getCards().size() == 2
                && h.getCards().get(0).getRank() == h.getCards().get(1).getRank();
    }

    public static boolean mayDoubleDown(Hand hand) {
        return getCardsValue(hand.getCardList()) <= 21 && hand.getCardList().size() == 2;
    }

    public static boolean mayHitAndStand(Hand hand) {
        return getCardsValue(hand.getCardList()) < 21;
    }

    /**
     * Compare the hand to dealers'.
     *
     * @param hand      players hand to compare
     * @param dealer cards to compare to
     * @return <br> -1 if player lost
     * <br> 0 if it's a push
     * <br> 1 if player won
     * <br> 2 if player has a BJ
     */
    public static int compareToDealer(BJHand h, Hand dealer) {
        int playerValue = getHandValue(h);
        int dealerValue = getHandValue(dealer);

        boolean playerBlackJack = hasBlackJack(h);
        // should the dealer's hand be checked with the fucntion hasBlackJack(Hand) instead of checking the value and size?
        boolean dealerBlackJack = dealerValue == 21 && dealer.getCards().size() == 2;

        if (playerValue > 21) {
            return -1;
        }
        if (playerBlackJack && !dealerBlackJack) {
            return 2;
        }
        if (!playerBlackJack && dealerBlackJack) {
            return -1;
        }
        if (dealerValue > 21) {
            return 1;
        }
        if (playerValue == dealerValue) {
            return 0;
        }
        if (playerValue > dealerValue) {
            return 1;
        }
        return -1;
    }

    public static int getHandValue(Hand hand) {
        if (hand == null) {
            throw new IllegalArgumentException("hand must not be null.");
        }

        int value = 0;
        int aces = 0;

        for (Card c : hand.getCards()) {
            Integer cardValue = CARD_VALUES.get(c.getRank());
            if (cardValue == null) {
                throw new IllegalArgumentException("Unknown rank: " + c.getRank());
            }
            value += cardValue;
            if (c.getRank() == 'A') {
                aces++;
            }
        }

        // Upgrade aces from 1 to 11 where possible without busting.
        while (aces > 0 && value + 10 <= 21) {
            value += 10;
            aces--;
        }

        return value;
    }

    public static boolean hasBlackJack(Hand hand) {
        return getCardsValue(hand.getCardList()) == 21 && hand.getCardList().size() == 2;
    }
}
