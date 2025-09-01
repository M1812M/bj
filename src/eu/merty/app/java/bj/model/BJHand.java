package eu.merty.app.java.bj.model;

public class BJHand extends Hand {
    // TODO 11: Add unit tests for bet value logic and ensure correct handling of bet changes.
    private int betValue;

    public BJHand(Person owner) {
        super();
        if (owner == null)
            throw new NullPointerException("Owner is not instantiated.");
        this.betValue = 0;
    }

    public int getBetValue() {
        return this.betValue;
    }

    public void setBetValue(int betAmount) {
        if (this.betValue != 0)
            throw new UnsupportedOperationException("The bet is already set to.");
        // FIXME 12: Consider allowing bet increases for double down, but not arbitrary changes after initial bet.
        this.betValue = betAmount;
    }

    public void addBetValue(int deltaAmount) {
        if (deltaAmount < 0)
            throw new IllegalArgumentException("deltaAmount is negative.");
        // TODO 13: Validate that deltaAmount does not exceed player's available money.
        this.betValue += deltaAmount;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.betValue + ")";
    }
}
