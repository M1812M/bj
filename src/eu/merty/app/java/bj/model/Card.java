package eu.merty.app.java.bj.model;

import java.util.Arrays;
import java.util.List;

// TODO change access modifier to rather private
/**
 * @author Hugo
 *
 * Gamecard with the france face.
 */
public class Card {
    private char suit;
    private char rank;

    /**
     * Constructor
     *
     * @param s suit of the card (x-joker, s-spades, h-hearts, d-diamonds, c-clubs)
     * @param r rank of the card (X, A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2)
     */
    Card(char s, char r) throws IllegalArgumentException {
        List<Character> cardRank = Arrays.asList('X', 'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
        List<Character> cardSuit = Arrays.asList('x', 's', 'h', 'd', 'c');
        if (!cardSuit.contains(s) || !cardRank.contains(r)) {
            throw new IllegalArgumentException();
        }
        suit = s;
        rank = r;
    }

    char getSuit() {
        return suit;
    }

    char getRank() {
        return rank;
    }

    public String toString() {
        return String.valueOf(rank + suit);
    }
}
