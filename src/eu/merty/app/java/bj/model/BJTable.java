package eu.merty.app.java.bj.model;

public class BJTable extends Table {
    // TODO 18: Add method to display all seats and their occupants for UI feedback.
    private Dealer dealer;

    public BJTable(int numberOfSeats, int numberOfCardDecks) {
        super(numberOfSeats, new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks));
        this.dealer = new Dealer();
    }

    public void newDeck(int numberOfCardDecks) {
    this.deck = new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks);
    // FIXME 19: Ensure deck is shuffled and old cards are discarded properly.
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    @Override
    public String toString() {
        return "Dealer (" + dealer.getHand() + "), " + super.toString();
    }
}
