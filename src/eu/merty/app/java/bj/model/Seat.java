package eu.merty.app.java.bj.model;
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
}
