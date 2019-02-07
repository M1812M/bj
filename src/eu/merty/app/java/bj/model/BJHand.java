package eu.merty.app.java.bj.model;

public class BJHand extends Hand {
    private int bettingAmount;

    public BJHand(Person owner) {
        super();
        if (owner == null)
            throw new NullPointerException("Owner is not instantiated.");
        bettingAmount = 0;
    }

    public int getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(int betAmount) {
        if (bettingAmount != 0)
            throw new UnsupportedOperationException("The bet is already set to.");
        bettingAmount = betAmount;
    }

    public void increaseBettingAmount(int deltaAmount) {
        if (deltaAmount < 0)
            throw new IllegalArgumentException("deltaAmount is negative.");
        bettingAmount += deltaAmount;
    }

    @Override
    public String toString() {
        return this.getCards() + " (" + bettingAmount + ")";
    }
}
