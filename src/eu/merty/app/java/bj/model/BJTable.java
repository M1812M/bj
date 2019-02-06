package eu.merty.app.java.bj.model;

public class BJTable extends Table {
    private Dealer dealer;

    public BJTable(int numberOfSeats, int numberOfCardDecks) {
        super(numberOfSeats, new Deck(Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks));
        this.dealer = new Dealer();
    }

    public Hand getDealersHand() {
        return this.dealer.getHand();
    }

    public Hand removeDealersHand() {
        return this.dealer.removeHand();
    }

    @Override
    public String toString() {
        return "Dealer (" + dealer.getHand() + ") " + super.toString();
    }
}
