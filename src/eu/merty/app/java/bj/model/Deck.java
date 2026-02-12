package eu.merty.app.java.bj.model;

import java.util.*;

/**
 * Deck of cards with france faces. Variations are 52 or 32 cards.
 */
public class Deck {
    private static final List<String> FULL_DECK = Collections.unmodifiableList(Arrays.asList(
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
    ));

    private final List<Card> deck;
    private final Random random;

    private void shuffle() {
        Collections.shuffle(this.deck, random);
    }

    public enum CardDeckVariation {
        STANDARD_52(52), STANDARD_32(32);
        private final int size;

        CardDeckVariation(int size) {
            this.size = size;
        }

        int getSize() {
            return size;
        }
    }

    /**
     * Creates a deck of cards with shuffled order.
     *
     * @param variation     CardDeckVariation is needed for the number and kind of cards to pick from.
     * @param numberOfDecks Multiple card faces, but only form the same kind, and will be mixed together.
     */
    Deck(CardDeckVariation variation, int numberOfDecks) {
        if (variation == null) {
            throw new IllegalArgumentException("variation must not be null.");
        }
        if (numberOfDecks < 1) {
            throw new IllegalArgumentException("numberOfDecks must be at least 1.");
        }

        deck = new ArrayList<>();
        random = new Random();

        // add all cards within variation to the deck
        for (int i = 0; i < numberOfDecks; i++) {
            FULL_DECK.subList(0, variation.getSize())
                    .forEach((s) -> deck.add(new Card(s.charAt(1), s.charAt(0))));
        }
        this.shuffle();
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
        if (deck.isEmpty()) {
            throw new IllegalStateException("Deck is empty.");
        }
        return deck.remove(0);
    }
}
