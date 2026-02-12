package eu.merty.app.java.bj.model;

import java.util.Arrays;
import java.util.List;

/**
 * Game card with a french suit and rank notation.
 */
public class Card {
    private static final List<Character> VALID_RANKS = Arrays.asList(
            'X', 'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
    );
    private static final List<Character> VALID_SUITS = Arrays.asList('x', 's', 'h', 'd', 'c');

    private final char suit;
    private final char rank;

    /**
     * Constructor
     *
     * @param s suit of the card (x-joker, s-spades, h-hearts, d-diamonds, c-clubs)
     * @param r rank of the card (X, A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2)
     */
    Card(char s, char r) throws IllegalArgumentException {
        if (!VALID_SUITS.contains(s) || !VALID_RANKS.contains(r)) {
            throw new IllegalArgumentException("Invalid card: " + r + s);
        }
        this.suit = s;
        this.rank = r;
    }

    char getSuit() {
        return suit;
    }

    char getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return new String(new char[]{rank, suit});
    }
}
