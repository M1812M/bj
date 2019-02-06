package eu.merty.app.java.bj;

import java.util.Arrays;
import java.util.List;

public class Card {
    private char suit;
    private char rank;

    private List<Character> cardRank = Arrays.asList('X', 'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private List<Character> cardSuit = Arrays.asList('x', 's', 'h', 'd', 'c');

    /**
     * Constructor
     *
     * @param s suit of the card (x-joker, s-spades, h-hearts, d-diamonds, c-clubs)
     * @param r rank of the card (X, A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2)
     */
    Card(char s, char r) throws IllegalArgumentException {
        if (!cardSuit.contains(s) || !cardRank.contains(r)) {
            throw new IllegalArgumentException();
        }
        suit = s;
        rank = r;
    }

    public char getSuit() {
        return suit;
    }

    public char getRank() {
        return rank;
    }


    public String toString() {
        char[] c = {getRank(), getSuit()};
        return new String(c);
    }
}
