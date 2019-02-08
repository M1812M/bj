package eu.merty.app.java.bj.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CardTest {
    private static Card testCard;

    @org.junit.jupiter.api.BeforeAll
    static void initAll() {
        testCard = new Card('h', 'A');
    }

    @Test
    void wrongInput() {
        try {
            new Card('x', 'g');
            fail("Exception is expected for wrong rank.");
        } catch (IllegalArgumentException e) {
        }

        try {
            new Card('f', 'K');
            fail("Exception is expected for wrong suit.");
        } catch (IllegalArgumentException e) {
        }

        try {
            new Card('s', 'R');
            fail("Exception is expected for wrong arguments.");
        } catch (IllegalArgumentException e) {
        }
    }

    @org.junit.jupiter.api.Test
    void getSuit() {
        assertEquals('h', testCard.getSuit());
    }

    @org.junit.jupiter.api.Test
    void getRank() {
        assertEquals('A', testCard.getRank());
    }
}
