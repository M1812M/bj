package eu.merty.app.java.bj.model;

import eu.merty.app.java.cardgame.Hand;

public class BJHand extends Hand {
    private int betAmount;

    public BJHand(int betAmount) {
        super();
        this.betAmount = betAmount;
    }

    public int getBetAmount() {
        return this.betAmount;
    }

    public void changeBetAmountByDelta(int deltaAmount) {
        if (this.betAmount + deltaAmount < 0)
            throw new IllegalArgumentException("Negative bet isn't possible.");
        this.betAmount += deltaAmount;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.betAmount + ")";
    }
}
