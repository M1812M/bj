package eu.merty.app.java.bj.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Table {
    protected Deck deck;
    private final Seat[] seatList;

    Table(int numberOfSeats, Deck deck) {
        if (numberOfSeats < 1 || deck == null || deck.getDeckSize() == 0) {
            throw new IllegalArgumentException("Wrong argument given.");
        }

        this.deck = deck;
        this.seatList = new Seat[numberOfSeats];
        for (int i = 0; i < numberOfSeats; i++) {
            seatList[i] = new Seat();
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public Seat[] getSeatList() {
        return seatList;
    }

    public int getOccupiedSeatsNumber() {
        int n = 0;
        for (Seat s : seatList) {
            n += s.isEmpty() ? 0 : 1;
        }
        return n;
    }

    @Override
    public String toString() {
        return Arrays.stream(seatList)
                .map(Seat::toString)
                .filter(seat -> !seat.isEmpty())
                .collect(Collectors.joining(", "));
    }
}
