package eu.merty.app.java.bj.model;

import java.util.Dictionary;
import java.util.Hashtable;

public class Deck {
    static final Dictionary<Character, Integer> DOPPELKOPF_VALUE = new Hashtable<Character, Integer>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 10);
        put('Q', 10);
        put('K', 10);
        put('A', 11);
    }};

    static final String[] FULL_DECK = new String[]{
            "Xx", "Xx", "Xx",
            "2c", "2d", "2h", "2s",
            "3c", "3d", "3h", "3s",
            "4c", "4d", "4h", "4s",
            "5c", "5d", "5h", "5s",
            "6c", "6d", "6h", "6s",
            "7c", "7d", "7h", "7s",
            "8c", "8d", "8h", "8s",
            "9c", "9d", "9h", "9s",
            "Tc", "Td", "Th", "Ts",
            "Jc", "Jd", "Jh", "Js",
            "Qc", "Qd", "Qh", "Qs",
            "Kc", "Kd", "Kh", "Ks",
            "Ac", "Ad", "Ah", "As"
    };
    private ArraySet<Card> deck;

    Deck(CardDeckVariation variation, int numberOfDecks) {
        String[] cardDeckArray = new String[variation.value];
        deck = new ArraySet<Card>();
        System.arraycopy(FULL_DECK, FULL_DECK.length - variation.value, cardDeckArray, 0, variation.value);
        for (int i = numberOfDecks; i > 0; i--) {
            for (String cardChars : cardDeckArray) {
                deck.add(new Card(DOPPELKOPF_VALUE.get(cardChars.charAt(0)), cardChars.charAt(0), cardChars.charAt(1)));
            }
        }
    }

    public int getDeckSize() {
        return deck.size();
    }

    public Card drawCard() {
        return deck.removeRandom();
    }

    public enum CardDeckVariation {
        STANDARD_52(52), STANDARD_32(32);
        private int value;

        CardDeckVariation(int size) {
            this.value = size;
        }
    }
}
