package eu.merty.app.java.bj.model;

public abstract class BJRuleset {
    public static boolean isAllowedToSplit(BJHand h) {
        return isAllowedToHitAndStand(h) && h.getHand().size() == 2
                && h.getHand().get(0).getRank() == h.getHand().get(1).getRank();
    }

    public static boolean isAllowedToDoubleDown(BJHand h) {
        return isAllowedToHitAndStand(h) && h.getHand().size() == 2;
    }

    public static boolean isAllowedToHitAndStand(BJHand h) {
        return h.getHandvalue() < 21;
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
        if (h.getHandvalue() > 21)
            return -1;
        else if (h.getHandvalue() == dealer.getHandvalue())
            return 0;
        else if (h.getHandvalue() == 21 && dealer.getHand().size() == 2)
            return 2;
        else if (h.getHandvalue() > dealer.getHandvalue())
            return 1;
        else if (dealer.getHandvalue() > 21)
            return 1;
        else
            return -1;
    }
}
