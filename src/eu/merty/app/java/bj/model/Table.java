package eu.merty.app.java.bj.model;

abstract class Table {
    Deck deck;
    Seat[] seatList;

    protected Table(int numberOfSeats, Deck deck) {
        if (numberOfSeats < 1 || deck == null || deck.getDeckSize() == 0)
            throw new IllegalArgumentException("Wrong argument given.");
        this.deck = deck;
        this.seatList = new Seat[numberOfSeats];
    }

    public Deck getDeck() {
        return deck;
    }

    public Seat[] getSeatList() {
        return seatList;
    }
}
