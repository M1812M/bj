package eu.merty.app.java.bj.model;

import java.util.Dictionary;
import java.util.Hashtable;

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

    public static boolean maySplit(BJHand h) {
    return mayHitAndStand(h) && h.getCards().size() == 2
        && h.getCards().get(0).getRank() == h.getCards().get(1).getRank();
    }

    public static boolean mayDoubleDown(BJHand h) {
        return getHandValue(h) <= 21 && h.getCards().size() == 2;
    }

    public static boolean mayHitAndStand(BJHand h) {
        return getHandValue(h) < 21;
    }

    /**
     * Compare the hand to dealers'.
     *
     * @param h      players hand to compare
     * @param dealer cards to compare to
     * @return <br> -1 if player lost
     * <br> 0 if it's a push
     * <br> 1 if player won
     * <br> 2 if player has a BJ
     */
    public static int compareToDealer(BJHand h, Hand dealer) {
        if (getHandValue(h) > 21)
            return -1;
        else if (getHandValue(h) == getHandValue(dealer))
            return 0;
        else if (getHandValue(h) == 21 && dealer.getCards().size() == 2)
            return 2;
        else if (getHandValue(h) > getHandValue(dealer))
            return 1;
        else if (getHandValue(dealer) > 21)
            return 1;
        else
            return -1;
    }

    public static int getHandValue(Hand hand) {
        int value = 0;
        boolean hasAce = false;
        for (Card c : hand.getCards()) {
            value += DOPPELKOPF_VALUE.get(c.getRank());
            if (c.getRank() == 'A')
                hasAce = true;
        }
        if (hasAce)
            value += value < 11 ? 11 : 0;
        // FIXME 3: Correct ace handling for multiple aces and soft/hard hand values (use +10 when it doesn't bust).
        return value;
    }

    public static boolean hasBlackJack(BJHand h) {
        return getHandValue(h) == 21 && h.getCards().size() == 2;
    }
}
