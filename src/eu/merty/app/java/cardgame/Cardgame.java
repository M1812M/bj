package eu.merty.app.java.cardgame;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// TODO Cardgame should hold the player on seats with their hands for their seats.
public abstract class Cardgame {
    protected Deck deck;
    protected Seat[] seatList;

    protected Cardgame(int numberOfSeats, Deck.CardDeckVariation deckVariation, int numberOfDecks) {
        if (numberOfSeats < 1)
            throw new IllegalArgumentException("Wrong argument given.");
        this.deck = new Deck(deckVariation, numberOfDecks);
        this.seatList = new Seat[numberOfSeats];
        for (int i = 0; i < numberOfSeats; i++)
            seatList[i] = new Seat();
    }

    public void renewDeck() {
        this.deck = new Deck(this.deck.getDeckVariation(), this.deck.getNumberOfDecks());
    }

    public void dealCards() {
        int handsCount = Arrays
                .stream(seatList)
                .filter(Predicate.not(Seat::isEmpty))
                .mapToInt(seat -> seat.getHandList().size())
                .sum();
        if (deck.getDeckSize() < handsCount)
            throw new IndexOutOfBoundsException("Not enough cards in deck.");
        Arrays
                .stream(seatList)
                .filter(Objects::nonNull)
                .forEach(
                        player -> player
                                .getHandList()
                                .forEach(hand -> hand.addCard(this.deck.drawCard())));
    }

    public void sitPlayer(Player player, int position) {
        if (position < 0 || position > seatList.length)
            throw new IndexOutOfBoundsException("Not as many seats available. Available: " + seatList.length + "; Asked: " + (position - 1));
        seatList[position].sitDown(player);
    }

    public void dealCard(Hand hand) {
        hand.addCard(this.deck.drawCard());
    }

    public Seat[] getSeatList() {
        return seatList;
    }

    public int getOccupiedSeatsNumber() {
        int count = 0;
        for (Seat s : seatList)
            if (!s.isEmpty())
                count++;
        return count;
    }

    public String toString() {
        int sCnt = 1;
        StringBuilder strB = new StringBuilder();
        for (Seat seat : seatList) {
            strB
                    .append(sCnt > 1 ? "\n" : "")
                    .append(sCnt++)
                    .append(". ")
                    .append(seat.toString());
        }
        return strB.toString();
    }
}
