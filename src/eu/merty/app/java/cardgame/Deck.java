package eu.merty.app.java.cardgame;

import java.util.*;

/**
 * @author Hugo
 * <p>
 * Deck of cards with france faces. Variations are 52 or 32 cards.
 */
public class Deck {
    private static final List<String> FULL_DECK = Arrays.asList(
            "Ac", "Ad", "Ah", "As",
            "Kc", "Kd", "Kh", "Ks",
            "Qc", "Qd", "Qh", "Qs",
            "Jc", "Jd", "Jh", "Js",
            "Tc", "Td", "Th", "Ts",
            "9c", "9d", "9h", "9s",
            "8c", "8d", "8h", "8s",
            "7c", "7d", "7h", "7s",
            "6c", "6d", "6h", "6s",
            "5c", "5d", "5h", "5s",
            "4c", "4d", "4h", "4s",
            "3c", "3d", "3h", "3s",
            "2c", "2d", "2h", "2s",
            "Xx", "Xx", "Xx"
    );
    private Deck.CardDeckVariation deckVariation;
    private int numberOfDecks;
    private ArrayList<Card> deck;

    /**
     * Creates a deck of cards with shuffled order.
     *
     * @param variation     CardDeckVariation is needed for the number and kind of cards to pick from.
     * @param numberOfDecks Multiple card faces, but only form the same kind, and will be mixed together.
     */
    Deck(CardDeckVariation variation, int numberOfDecks) {
        deck = new ArrayList<>();
        // add all cards within variation to the deck
        this.deckVariation = variation;
        this.numberOfDecks = numberOfDecks;
        for (int i = numberOfDecks; i > 0; i--)
            FULL_DECK.subList(0, variation.value).forEach((s) -> deck.add(new Card(s.charAt(1), s.charAt(0))));
        this.shuffle();
    }

    private void shuffle() {
        Collections.shuffle(this.deck, new Random(System.currentTimeMillis()));
    }

    public Deck.CardDeckVariation getDeckVariation() {
        return this.deckVariation;
    }

    public int getNumberOfDecks() {
        return this.numberOfDecks;
    }

    /**
     * @return Deck size.
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     * Drawing a random card and removing it from the deck.
     *
     * @return Card which is drawn from the top of deck.
     */
    public Card drawCard() {
        return deck.remove(0);
    }

    public enum CardDeckVariation {
        STANDARD_52(52), STANDARD_32(32);
        private int value;

        CardDeckVariation(int size) {
            this.value = size;
        }
    }
}
