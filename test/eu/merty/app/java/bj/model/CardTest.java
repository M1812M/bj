package eu.merty.app.java.bj.model;

import eu.merty.app.java.bj.model.Card;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private static Card testCard;

    @org.junit.jupiter.api.BeforeAll
    static void initAll() {
        testCard = new Card(1, 'h', 'A');
    }

    @org.junit.jupiter.api.Test
    void getSuit() {
        assertEquals('h', testCard.getSuit());
    }

    @org.junit.jupiter.api.Test
    void getRank() {
        assertEquals('A', testCard.getRank());
    }

    @org.junit.jupiter.api.Test
    void getValue() {
        assertEquals(1, testCard.getValue());
    }
}
