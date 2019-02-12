package eu.merty.app.java.cardgame;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO Cardgame should hold the player on seats with their hands for their seats.
public abstract class Cardgame {
    protected Deck deck;
    private Player[] playerList;

    Cardgame(int numberOfPlayer, Deck deck) {
        if (numberOfPlayer < 1 || deck == null || deck.getDeckSize() == 0)
            throw new IllegalArgumentException("Wrong argument given.");
        this.deck = deck;
        this.playerList = new Player[numberOfPlayer];
        for (int i = 0; i < numberOfPlayer; i++)
            playerList[i] = new Player();
    }

    public void dealCards() {
        int handsCount = Arrays
                .stream(playerList)
                .filter(Objects::nonNull)
                .mapToInt(player -> player.getHandList().size())
                .sum();
        if (deck.getDeckSize() < handsCount)
            throw new IndexOutOfBoundsException("Not enough cards in deck.");
        Arrays
                .stream(playerList)
                .filter(Objects::nonNull)
                .forEach(
                        player -> player
                                .getHandList()
                                .forEach(hand -> hand.addCard(this.deck.drawCard())));
    }

    public void sitPlayer(Player player, int position) {
        position--;
        if (position < 0 || position > playerList.length)
            throw new IndexOutOfBoundsException("Not as many seats available. Available: " + playerList.length + "; Asked: " + (position - 1));
        playerList[position] = player;
    }

    public Player[] getPlayerList() {
        return playerList;
    }

    public int getOccupiedSeatsNumber() {
        int n = 0;
        for (Player s : playerList)
            n += s.isEmpty() ? 0 : 1;
        return n;
    }

    public String toString() {
        return Arrays
                .stream(playerList)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
