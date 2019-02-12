package eu.merty.app.java.bj.model;

import eu.merty.app.java.cardgame.Hand;

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
