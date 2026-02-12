package eu.merty.app.java.bj.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardTest {
    private static Card testCard;

    @org.junit.jupiter.api.BeforeAll
    static void initAll() {
        testCard = new Card('h', 'A');
    }

    @Test
    void wrongInput() {
        assertThrows(IllegalArgumentException.class, () -> new Card('x', 'g'));
        assertThrows(IllegalArgumentException.class, () -> new Card('f', 'K'));
        assertThrows(IllegalArgumentException.class, () -> new Card('s', 'R'));
    }

    @Test
    void getSuit() {
        assertEquals('h', testCard.getSuit());
    }

    @Test
    void getRank() {
        assertEquals('A', testCard.getRank());
    }
}
