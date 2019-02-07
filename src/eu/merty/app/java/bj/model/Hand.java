package eu.merty.app.java.bj.model;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    private List<Card> hand;

    public Hand() {
        hand = new LinkedList<Card>();
    }

    public List<Card> getCards() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean removeCard(Card card) {
        return hand.remove(card);
    }

    public String toString() {
        return String.join(", ", hand.toString());
    }
}
