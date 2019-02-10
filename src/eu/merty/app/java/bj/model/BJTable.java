package eu.merty.app.java.bj.model;

public class BJTable extends Table {
    private Dealer dealer;

    public BJTable(int numberOfSeats, int numberOfCardDecks) {
        super(numberOfSeats, new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks));
        this.dealer = new Dealer();
    }

    public void newDeck(int numberOfCardDecks) {
        this.deck = new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks);
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    @Override
    public String toString() {
        return "Dealer (" + dealer.getHand() + "), " + super.toString();
    }
}
