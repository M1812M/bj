package eu.merty.app.java.bj.model;
public class Hand {
    protected List<Card> hand;
    private int handvalue;

    Hand() {
        handvalue = 0;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
        handvalue += card.getValue();
    }

    public boolean removeCard(Card card) {
        return hand.remove(card);
    }

    public int getHandvalue() {
        if (handvalue > 21) {
            int value = handvalue;
            for (Card c : hand) {
                value -= c.getRank() == 'A' ? 10 : 0;
                if (value <= 21)
                    return value;
            }
        }
        return handvalue;
    }

    public String toString() {
        return String.join(", ", hand.toString());
    }
}
