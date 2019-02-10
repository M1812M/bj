package eu.merty.app.java.bj.model;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    private List<Card> heldCards;

    public Hand() {
        heldCards = new LinkedList<Card>();
    }

    public Hand(List<Card> cards) {
        heldCards = cards;
    }

    List<Card> getCards() {
        return heldCards;
    }

    public void addCard(Card card) {
        heldCards.add(card);
    }

    public Card removeCard(int index) {
        return heldCards.remove(index);
    }

    public String toString() {
        return String.join(", ", heldCards.toString());
    }
}
