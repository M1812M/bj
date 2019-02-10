package eu.merty.app.java.bj.model;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void removeHand() {
        this.hand = new Hand();
    }
}
