package eu.merty.app.java.bj.model;

public class Seat {
    private int position;
    private String playerName;
    // private List<Hand> handList;

    public Seat(int position) {
        this.position = position;
        // this.handList = new LinkedList<Hand>();
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
        String name = playerName == null ? "empty" : playerName;
        return position + ". " + name;
/*
        + " (" +
                handList
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
*/
    }

    public int getPosition() {
        return position;
    }
}
