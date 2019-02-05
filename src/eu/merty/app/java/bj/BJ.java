package eu.merty.app.java.bj;

public class BJ {
    private Dealer dealer;
    private List<Player> player;
    private Deck deck;

    public void BJ() {
        dealer = new Dealer();
        deck = new Deck(Deck.STANDARD_52);
    }
}
