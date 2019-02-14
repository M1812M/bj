package eu.merty.app.java.bj.model;

import java.util.LinkedList;
import java.util.List;

public class Hand {
    private String owner;
    private List<Card> cardList;
    private int betAmount;

    public Hand(String owner, List<Card> cardList, int betAmount) {
        this.owner = owner;
        this.cardList = cardList;
        this.betAmount = betAmount;
    }

    public Hand(String owner) {
        this.owner = owner;
        cardList = new LinkedList<Card>();
        betAmount = 0;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public int getBetAmount() {
        return this.betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    /*
        public int getValue(ToIntFunction<Card> function) {
            return this.cardList.stream().mapToInt(function).sum();
        }
    */
    public String toString() {
        return String.join(",", cardList.toString()) + "(" + this.betAmount + ")";
    }
}
