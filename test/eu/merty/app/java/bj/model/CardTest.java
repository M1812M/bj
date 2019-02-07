package eu.merty.app.java.bj.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    private static Card testCard;

    @org.junit.jupiter.api.BeforeAll
    static void initAll() {
        testCard = new Card('h', 'A');
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
