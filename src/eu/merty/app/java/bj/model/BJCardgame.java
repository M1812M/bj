package eu.merty.app.java.bj.model;

import eu.merty.app.java.cardgame.Cardgame;
import eu.merty.app.java.cardgame.Deck;
import eu.merty.app.java.cardgame.Hand;
import eu.merty.app.java.cardgame.Seat;

public class BJCardgame extends Cardgame {
    private Dealer dealer;

    public BJCardgame(int numberOfSeats, int numberOfCardDecks) {
        super(numberOfSeats, Deck.CardDeckVariation.STANDARD_52, numberOfCardDecks);
        this.dealer = new Dealer();
    }

    public Hand getDealersHand() {
        return dealer.getHand();
    }

    public void resetGame() {
        this.renewDeck();
        dealer.removeHand();
        for (Seat s : seatList)
            s.clearHands();
    }

    public void dealDealer() {
        this.dealer.getHand().addCard(this.deck.drawCard());
    }

    @Override
    public String toString() {
        return super.toString() + "\nDealer (" + dealer.getHand() + ")";
    }
}
