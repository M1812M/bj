package eu.merty.app.java.cardgame;

import java.util.LinkedList;
import java.util.List;
import java.util.function.ToIntFunction;

public class Hand {
    private List<Card> heldCards;

    public Hand() {
        heldCards = new LinkedList<Card>();
    }

    public Hand(List<Card> cards) {
        heldCards = cards;
    }

    public void addCard(Card card) {
        heldCards.add(card);
    }

    public Card removeCard(int index) {
        return heldCards.remove(index);
    }

    List<Card> getCards() {
        return heldCards;
    }

    public List<Character> getRanks(){
        LinkedList<Character> ranks = new LinkedList<Character>();
        this.heldCards.forEach(c -> ranks.addLast(c.getRank()));
        return ranks;
    }

    public int getValue(ToIntFunction<Card> function){
        return this.heldCards.stream().mapToInt(function).sum();
    }

    public String toString() {
        return String.join(",", heldCards.toString());
    }
}
