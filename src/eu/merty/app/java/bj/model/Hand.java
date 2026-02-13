package eu.merty.app.java.bj.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Hand {
    private final List<Card> heldCards;

    public Hand() {
        heldCards = new LinkedList<>();
    }

    public Hand(List<Card> cards) {
        if (cards == null) {
            throw new IllegalArgumentException("cards must not be null.");
        }
        heldCards = new LinkedList<>(cards);
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("card must not be null.");
        }
        heldCards.add(card);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    List<Card> getCards() {
        return Collections.unmodifiableList(heldCards);
    }

    public List<Character> getRanks() {
        LinkedList<Character> ranks = new LinkedList<>();
        this.heldCards.forEach(c -> ranks.addLast(c.getRank()));
        return ranks;
    }

    public int getValue(ToIntFunction<Card> function) {
        if (function == null) {
            throw new IllegalArgumentException("function must not be null.");
        }
        return this.heldCards.stream().mapToInt(function).sum();
    }

    @Override
    public String toString() {
        return heldCards.toString();
    }
}
