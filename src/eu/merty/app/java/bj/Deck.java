package eu.merty.app.java.bj;

import java.util.*;

public class Deck {
    static final List<String> FULL_DECK = Arrays.asList(
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
    private ArrayList<Card> deck = new ArrayList<>();

    Deck(CardDeckVariation variation, int numberOfDecks) {
        for (int i = numberOfDecks; i > 0; i--) {
            for (String cardChars : FULL_DECK.subList(0, variation.value - 1)) {
                deck.add(new Card(cardChars.charAt(1), cardChars.charAt(0)));
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
