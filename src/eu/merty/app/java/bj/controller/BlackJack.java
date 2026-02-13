package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BlackJack {
    protected Deck deck;
    protected List<Seat> seatList;
    private List<Card> dealerCards;
    private List<Hand> handList;
    private List<Player> playerList;

    public BlackJack(int numberOfSeats, int numberOfCardDecks) {
        if (numberOfSeats < 1)
            throw new IllegalArgumentException("Wrong argument given.");
        this.deck = new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks);
        this.seatList = new ArrayList<Seat>();
        for (int i = 0; i < numberOfSeats; i++)
            seatList.add(new Seat(i + 1));
        this.dealerCards = new LinkedList<Card>();
    }

    public void cleanRound() {
        renewDeck();
        dealerCards = new LinkedList<Card>();
        for (Hand hand : handList)
            hand.setCardList(new LinkedList<Card>());
    }

    public void dealCards() {
        if (deck.getDeckSize() < handList.size())
            throw new IndexOutOfBoundsException("Not enough cards in deck.");

        for (Hand hand : handList)
            dealCard(hand);
    }

    public void dealCard(Hand hand) {
        if (deck.getDeckSize() == 0)
            throw new IndexOutOfBoundsException("Deck is empty.");
        hand.getCardList().add(deck.drawCard());
    }

    public void dealDealer() {
        dealerCards.add(this.deck.drawCard());
    }

    public void renewDeck() {
        this.deck = new Deck(this.deck.getDeckVariation(), this.deck.getNumberOfDecks());
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void sitPlayer(Player player, int position) {
        if (position < 0 || position > seatList.size())
            throw new IndexOutOfBoundsException("Not as many seats available. Available: " + seatList.size() + "; Asked: " + position);
        seatList.stream().filter(seat -> seat.getPosition() == position).forEach(seat -> seat.sitDown(player));
    }

    public void emptySeat(int position) {
        seatList.get(position).leaveSeat();
    }

    public int getOccupiedSeatsNumber() {
        int count = 0;
        for (Seat s : seatList)
            if (!s.isEmpty())
                count++;
        return count;
    }

    public List<Hand> getHandList() {
        return handList;
    }

    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB
                .append("Dealer [")
                .append(dealerCards)
                .append("]");

        for (Seat seat : seatList) {
            {
                strB
                        .append("\n")
                        .append(seat.getPosition())
                        .append(".")
                        .append(seat.getPlayersName())
                        .append(" ");
                List<Hand> handsAtSeat = handList.stream().filter(hand -> seat.getPlayersName().equals(hand.getOwner())).collect(Collectors.toList());
                handsAtSeat.stream().map(hand -> hand.toString()).collect(Collectors.joining(", "));
            }
        }
        return strB.toString();
    }
}
