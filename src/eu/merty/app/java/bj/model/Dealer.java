package eu.merty.app.java.bj.model;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public Hand removeHand() {
        Hand tmpHand = this.hand;
        this.hand = new Hand();
        return tmpHand;
    }
}
