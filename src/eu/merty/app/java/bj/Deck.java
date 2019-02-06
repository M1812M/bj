package eu.merty.app.java.bj;

import java.util.*;

public class Deck {
    private static final Dictionary<Character, Integer> DOPPELKOPF_VALUE = new Hashtable<Character, Integer>() {{
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
        put('A', 1);
    }};
    static final List<String> FULL_DECK = Arrays.asList(
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
    );
    private ArrayList<Card> deck = new ArrayList<>();

    Deck(CardDeckVariation variation, int numberOfDecks) {
        for (int i = numberOfDecks; i > 0; i--) {
            for (String cardChars : FULL_DECK.subList(FULL_DECK.size() - variation.value, FULL_DECK.size())) {
                deck.add(new Card(DOPPELKOPF_VALUE.get(cardChars.charAt(0)), cardChars.charAt(0), cardChars.charAt(1)));
            }
        }
        this.shuffle();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public Card drawCard() {
        return deck.remove(0);
    }

    private void shuffle() {
        Collections.shuffle(this.deck, new Random(System.currentTimeMillis()));
    }

    public enum CardDeckVariation {
        STANDARD_52(52), STANDARD_32(32);
        private int value;

        CardDeckVariation(int size) {
            this.value = size;
        }
    }
}