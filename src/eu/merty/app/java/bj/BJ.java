package eu.merty.app.java.bj;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BJ {
    private static Scanner scanner = new Scanner(System.in);
    private static final Dictionary<Character, Integer> DOPPELKOPF_VALUE = new Hashtable<Character, Integer>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 10);
        put('Q', 10);
        put('K', 10);
        put('A', 1);
    }};

    public static void main(String[] args) {
        int money = 100;
        Deck deck = new Deck(Deck.CardDeckVariation.STANDARD_52, 4);
        do {
            ArrayList<Card> player = new ArrayList<Card>();
            ArrayList<Card> dealer = new ArrayList<Card>();
            int bet = Integer.valueOf(ask("How much to bet, your money is " + money + "?"));

            bet = money - bet < 0 ? money : bet;
            money -= bet;
            player.add(deck.drawCard());
            update(dealer, player);
            dealer.add(deck.drawCard());
            update(dealer, player);
            player.add(deck.drawCard());
            update(dealer, player);

            boolean done = handValue(player) == 21 && player.size() == 2;
            while (!done) {
                switch (ask("What to do? (hit, stand, doubleD)")) {
                    case "hit":
                        if (handValue(player) < 21) {
                            player.add(deck.drawCard());
                        }
                        break;
                    case "stand":
                        done = true;
                        break;
                    case "doubleD":
                        if (money >= bet && handValue(player) <= 21) {
                            player.add(deck.drawCard());
                            money -= bet;
                            bet += bet;
                        } else {
                            message("Not possible.");
                            break;
                        }
                        done = true;
                        break;
                    default:
                        message("try again");
                }
                update(dealer, player);
                if (handValue(player) > 21)
                    done = true;
            }

            int dealerV;
            do {
                dealer.add(deck.drawCard());
                dealerV = handValue(dealer);

                update(dealer, player);
            } while (dealerV < 17);

            int playerV = handValue(player);
            if (playerV > 21) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Lost");
            } else if (dealerV > 21) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Won");
                money += bet * 2;
            } else if (dealerV == playerV) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Push");
                money += bet;
            } else if (dealerV < playerV) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Won");
                money += bet * 2;
            } else { //if (dealerV > playerV) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Lost");
            }

            if (deck.getDeckSize() < 52) {
                deck = new Deck(Deck.CardDeckVariation.STANDARD_52, 4);
                message("A new deck got served.");
            }
        }
        while (money > 0);
    }

    private static int handValue(@NotNull ArrayList<Card> cards) {
        int value = 0;
        boolean hasAce = false;
        for (Card c : cards) {
            value += DOPPELKOPF_VALUE.get(c.getRank());
            if (c.getRank() == 'A')
                hasAce = true;
        }
        if (hasAce)
            value += value < 11 ? 11 : 0;
        return value;
    }

    private static void update(ArrayList<Card> dealer, ArrayList<Card> player) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String dM = handToString(dealer);
        String pM = handToString(player);
        message("dealer " + dM + " and player " + pM);
    }

    private static String handToString(@NotNull ArrayList<Card> cards) {
        LinkedList<String> cardString = new LinkedList<>();
        for (Card c : cards) {
            cardString.add(c.toString());
        }
        return String.join(", ", cardString);
    }

    private static String ask(String question) {
        message(question);
        String answer = "";
        if (scanner.hasNextLine())
            answer = scanner.nextLine();
        return answer;
    }

    private static void message(String message) {
        System.out.println(message);
    }
}
