package eu.merty.app.java.bj.model;

public class Seat {
    private Person owner;
    private final List<BJHand> handList;

    public Seat() {
        handList = new LinkedList<>();
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
        handList.clear();
    }

    public Person getOwner() {
        return owner;
    }

    public void freeOwner() {
        owner = null;
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

    /*
        public List<Hand> getHandList() {
            return handList;
        }

        public void addHand(Hand h) {
            handList.add(h);
        }

        public boolean removeHand(Hand h) {
            return handList.remove(h);
        }

        public void clearHands() {
            handList = new LinkedList<>();
        }
    */
    @Override
    public String toString() {
        if (owner == null) {
            return "";
        }

        if (handList.isEmpty()) {
            return owner.getName();
        }

        return owner.getName() + " (" +
                handList.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "))
                + ")";
    }
}
