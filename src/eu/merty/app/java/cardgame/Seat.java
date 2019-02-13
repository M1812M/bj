package eu.merty.app.java.cardgame;

import eu.merty.app.java.bj.model.BJHand;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Seat {
    private String playerName;
    private List<Hand> handList;

    public Seat() {
        this.handList = new LinkedList<Hand>();
    }

    public boolean isEmpty() {
        return this.playerName == null;
    }

    public void leaveSeat() {
        this.playerName = null;
    }

    public void sitDown(Player player) {
        this.playerName = player.getName();
    }

    public String getPlayersName() {
        return this.playerName;
    }

    public List<Hand> getHandList() {
        return handList;
    }

    public void addHand(BJHand h) {
        handList.add(h);
    }

    public boolean removeHand(BJHand h) {
        return handList.remove(h);
    }

    public void clearHands() {
        handList = new LinkedList<>();
    }

    @Override
    public String toString() {
        String name = playerName == null ? "empty" : playerName;
        return name + " (" +
                handList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }
}
