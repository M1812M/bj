package eu.merty.app.java.bj.model;

public class Card {
    private final int value;
    private final char suit;
    private final char face;

    Card(int v, char f, char s) {
        value = v >= 0 ? v : 0;
        face = "AKQJT98765432X".indexOf(f) >= 0 ? f : '_';
        suit = "dschx".indexOf(s) >= 0 ? s : '_';
    }

    public char getSuit() {
        return suit;
    }

    public char getRank() {
        return face;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new String(new char[]{getRank(), getSuit()});
    }
}
