package eu.merty.app.java.cardgame;

import eu.merty.app.java.bj.model.BJHand;
import eu.merty.app.java.bj.model.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private Person owner;
    private List<Hand> handList;

    public Player() {
        handList = new LinkedList<>();
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

    public Person getOwner() {
        return owner;
    }

    public void freeOwner() {
        owner = null;
    }

    public boolean isEmpty() {
        return owner == null;
    }

    public Player sitOwner(Person person) {
        this.owner = person;
        return this;
    }

    @Override
    public String toString() {
        if (owner == null)
            return "";
        return owner.getName() + " (" + handList.stream().map(Object::toString).collect(Collectors.joining(", ", "[", "]")) + ")";
    }
}
