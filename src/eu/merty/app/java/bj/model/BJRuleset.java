package eu.merty.app.java.bj.model;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BJRuleset {
    private static final Dictionary<Character, Integer> DOPPELKOPF_VALUE = new Hashtable<Character, Integer>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 10);
        put('Q', 10);
        put('K', 10);
        put('A', 1);
    }};

    public static boolean maySplit(Hand hand) {
        List<Card> cards = hand.getCardList();
        return mayHitAndStand(hand) && cards.size() == 2
                && cards.get(0).getRank() == cards.get(1).getRank();
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
    public static int compareToDealer(Hand hand, List<Card> dealer) {
        if (getCardsValue(hand.getCardList()) > 21)
            return -1;
        else if (getCardsValue(hand.getCardList()) == getCardsValue(dealer))
            return 0;
        else if (getCardsValue(hand.getCardList()) == 21 && dealer.size() == 2)
            return 2;
        else if (getCardsValue(hand.getCardList()) > getCardsValue(dealer))
            return 1;
        else if (getCardsValue(dealer) > 21)
            return 1;
        else
            return -1;
    }

    // TODO replce getCardsValue with a function to be thrown on the cards for checking the value, but considering aces in a BlackJack.
    public static int getCardsValue(List<Card> cards) {
        int value = 0;
        boolean hasAce = false;
        for (char c : cards.stream().map(Card::getRank).collect(Collectors.toList())) {
            value += DOPPELKOPF_VALUE.get(c);
            if (c == 'A')
                hasAce = true;
        }
        if (hasAce)
            value += value < 11 ? 11 : 0;
        return value;
    }

    public static boolean hasBlackJack(Hand hand) {
        return getCardsValue(hand.getCardList()) == 21 && hand.getCardList().size() == 2;
    }
}
