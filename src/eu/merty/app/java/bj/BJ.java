package eu.merty.app.java.bj;

public class BJ {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int money = 100;
        Deck deck = new Deck(Deck.CardDeckVariation.STANDARD_52, 4);
        while (money > 0) {
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

            boolean done = false;
            int playerV = 0;
            int playerA = 0;
            while (!done) {
                switch (ask("What to do? (hit, stand, doubleD)")) {
                    case "hit":
                        playerV = 0;
                        playerA = 0;
                        for (Card c : player) {
                            playerV += c.getValue();
                            playerA += c.getSuit() == 'A' ? 1 : 0;
                        }
                        for (int i = playerA; i > 0; i--) {
                            playerV += playerV <= 11 ? 10 : 0;
                        }
                        if (playerV < 21) {
                            player.add(deck.drawCard());
                        }
                        break;
                    case "stand":
                        done = true;
                        break;
                    case "doubleD":
                        playerV = 0;
                        playerA = 0;
                        for (Card c : player) {
                            playerV += c.getValue();
                            playerA += c.getSuit() == 'A' ? 1 : 0;
                        }
                        for (int i = playerA; i > 0; i--) {
                            playerV += playerV <= 11 ? 10 : 0;
                        }

                        if (money >= bet && playerV <= 21) {
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
            }
            playerV = 0;
            playerA = 0;
            for (Card c : player) {
                playerV += c.getValue();
                playerA += c.getSuit() == 'A' ? 1 : 0;
            }
            for (int i = playerA; i > 0; i--) {
                playerV += playerV <= 11 ? 10 : 0;
            }

            int dealerV = 0;
            int dealerA = 0;
            do {
                dealer.add(deck.drawCard());

                dealerV = 0;
                dealerA = 0;
                for (Card c : dealer) {
                    dealerV += c.getValue();
                    dealerA += c.getSuit() == 'A' ? 1 : 0;
                }
                for (int i = dealerA; i > 0; i--) {
                    dealerV += dealerV <= 11 ? 10 : 0;
                }

                update(dealer, player);
            } while (dealerV < 17);

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
            } else if (dealerV > playerV) {
                message("dealers\' value " + dealerV + " and your is " + playerV);
                message("Lost");
            }

            if (deck.getDeckSize() < 52) {
                deck = new Deck(Deck.CardDeckVariation.STANDARD_52, 4);
                message("A new deck got served.");
            }
        }
    }

    public static String ask(String question) {
        message(question);
        String answer = "";
        if (scanner.hasNextLine())
            answer = scanner.nextLine();
        return answer;
    }

    static void update(ArrayList<Card> dealer, ArrayList<Card> player) {
        LinkedList<String> dML = new LinkedList<>();
        for (Card c : dealer) {
            dML.add(c.toString());
        }
        String dM = String.join(", ", dML);

        LinkedList<String> pML = new LinkedList<>();
        for (Card c : player) {
            pML.add(c.toString());
        }
        String pM = String.join(", ", pML);

        message("dealer " + dM + " and player " + pM);
    }

    public static void message(String message) {
        System.out.println(message);
    }
}