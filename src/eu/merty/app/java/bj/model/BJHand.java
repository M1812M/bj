package eu.merty.app.java.bj.model;

public class BJHand extends Hand {
    private int betValue;

    public BJHand(Person owner) {
        super();
        if (owner == null) {
            throw new NullPointerException("Owner is not instantiated.");
        }
        this.betValue = 0;
    }

    public int getBetValue() {
        return this.betValue;
    }

    public void setBetValue(int betAmount) {
        if (betAmount <= 0) {
            throw new IllegalArgumentException("betAmount must be positive.");
        }
        if (this.betValue != 0) {
            throw new UnsupportedOperationException("The bet is already set.");
        }

        this.betValue = betAmount;
    }

    public void addBetValue(int deltaAmount) {
        if (deltaAmount < 0) {
            throw new IllegalArgumentException("deltaAmount is negative.");
        }
        this.betValue += deltaAmount;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + this.betValue + ")";
    }
}
