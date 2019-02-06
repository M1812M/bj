package eu.merty.app.java.bj;

public class Card {
    private int value;
    private char suit;
    private char rank;

    /**
     * Constructor
     *
     * @param v value for counting
     * @param s suit of the card (s-spades, h-hearts, d-diamonds, c-clubs)
     * @param r rank of the card (A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2)
     */
    Card(int v, char s, char r) {
        value = v;
        suit = s;
        rank = r;
    }

    public char getSuit() {
        return suit;
    }

    public char getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        char[] c = {getSuit(), getRank()};
        return new String(c);
    }
}
