package eu.merty.app.java.bj.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Seat {
    private Person owner;
    private List<BJHand> handList;

    public Seat(Person owner) {
        this.owner = owner;
    }

    public List<BJHand> getHandList() {
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

    @Override
    public String toString() {
        return owner.getName() + " (" + handList.stream().map(Object::toString).collect(Collectors.joining(", ", "", "")) + ")";
    }
}
