package eu.merty.app.java.cardgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    @Test
    void checkConstructor(){
        Deck d = new Deck(Deck.CardDeckVariation.STANDARD_32,1);
        assertEquals(32, d.getDeckSize());
    }
}
