package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.*;
import eu.merty.app.java.bj.view.BJCommandLineUI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//import interfaces.Cardgame;

public class BJController {
    private final int NUMBER_OF_SEATS = 2;
    private final int NUMBER_OF_CARD_DECKS = 2;
    private final int PLAYER_START_MONEY = 20;
    private final String[] options = new String[]
            {"sit", "up", "run", "hit", "stand", "double", "split"};
    private BJTable table;
    private HashMap<String, Person> player;
    //  private CardGameUI ui;
    private BJCommandLineUI ui;
    // 0 sit - 1 up - 2 run - 3 hit - 4 stand - 5 doubleD - 6 split

    public BJController() {
        table = new BJTable(NUMBER_OF_SEATS, NUMBER_OF_CARD_DECKS);
        player = new HashMap<>();
        ui = new BJCommandLineUI();
    }

    public static void main(String[] args) {
        BJController bjController = new BJController();
        bjController.run();
    }

    public void run() {
        do {
            String userAction = ui.ask("These are your options: " + getRoundOptions());
            if (getRoundOptions().contains(userAction))
                doCommand(userAction);
        } while (table.getOccupiedSeatsNumber() > 0);
    }

    private String getRoundOptions() {
        List<String> options = new LinkedList<String>();
        if (table.getOccupiedSeatsNumber() < table.getSeatList().length) {
            options.add("sit");
        }
        if (table.getOccupiedSeatsNumber() > 0) {
            options.add("up");
        }
        if (table.getOccupiedSeatsNumber() > 0) {
            options.add("run");
        }

        return String.join(", ", options);
    }

    private List<String> getHandOptions(BJHand h, Person p) {
        List<String> options = new LinkedList<String>();
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("hit");
        }
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("stand");
        }
        if (p.getMoney() >= h.getBettingAmount() && BJRuleset.mayDoubleDown(h)) {
            options.add("double");
        }
        if (p.getMoney() >= h.getBettingAmount() && BJRuleset.maySplit(h)) {
            options.add("split");
        }

        return options;
    }

    public void doCommand(String command) {
        switch (command) {
            case "sit":
                String playerName = ui.ask("What is your name?");
                while (true) try {
                    sitPlayer(
                            playerName,
                            Integer.parseInt(ui.ask("On which of the " + NUMBER_OF_SEATS + " seat(s)?")) - 1);
                    break;
                } catch (Exception ignored) {
                    ui.err("Invalid seat number.");
                }
                break;
            case "up":
                while (true) {
                    try {
                        freeSeat(Integer.parseInt(ui.ask("Which seat should be freed?")));
                        break;
                    } catch (Exception ignored) {
                        ui.err("Seat is not occupied.");
                    }
                }
                break;
            case "run":
                playRound();
                break;
            default:
                ui.err("Could not found the command.");
        }
    }

    private void playRound() {
        if (table.getDeck().getDeckSize() < 52 * NUMBER_OF_CARD_DECKS / 2) {
            table.newDeck(NUMBER_OF_CARD_DECKS);
        }
        placeBets();
        placeCards();
        playHands();
        playDealer();
        payout();
    }

    private void payout() {
        int dealersHandValue = BJRuleset.getHandValue(table.getDealersHand());
        for (Seat s : table.getSeatList()) {
            for (BJHand h : s.getHandList()) {
                int playersHandValue = BJRuleset.getHandValue(h);

                // TODO implement an instant BJ with 50% extra.
                if (playersHandValue > 21) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " lost.");
                } else if (dealersHandValue > 21) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " won.");
                    s.getOwner().increaseMoney(h.getBettingAmount() * 2);
                } else if (dealersHandValue == playersHandValue) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " has a push.");
                    s.getOwner().increaseMoney(h.getBettingAmount());
                } else if (dealersHandValue < playersHandValue) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " won.");
                    s.getOwner().increaseMoney(h.getBettingAmount() * 2);
                } else { //if (dealerV > playerV) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " lost.");
                }
            }
        }
    }

    private void playDealer() {
        Hand h = table.getDealersHand();
        do {
            h.addCard(table.getDeck().drawCard());
            ui.draw(table);
        } while (BJRuleset.getHandValue(h) < 17);
    }

    private void playHands() {
        for (Seat s : table.getSeatList()) {
            for (BJHand h : s.getHandList()) {
                boolean done = false;
                while (!BJRuleset.hasBlackJack(h) && getHandOptions(h, s.getOwner()).size() > 0 && !done) {
                    String answer = ui.ask(
                            "What do you, " +
                                    s.getOwner().getName() +
                                    ", want to do for your hand (" + h + ")? " +
                                    String.join(", ", getHandOptions(h, s.getOwner())));
                    if (!getHandOptions(h, s.getOwner()).contains(answer)) {
                        switch (answer) {
                            case "hit":
                                h.addCard(table.getDeck().drawCard());
                                break;
                            case "stand":
                                done = true;
                                break;
                            case "double":
                                h.increaseBettingAmount(s.getOwner().decreaseMoney(h.getBettingAmount()));
                                break;
                            case "split":
                                BJHand tmpH = new BJHand(s.getOwner());
                                tmpH.setBettingAmount(s.getOwner().decreaseMoney(h.getBettingAmount()));
                                Card tmpC = h.getCards().get(0);
                                tmpH.addCard(tmpC);
                                h.removeCard(tmpC);
                                s.addHand(tmpH); // to debug, if taking for next hand to play
                                break;
                            default:
                                ui.message("Try again!");
                                break;
                        }
                        ui.draw(table);
                    }
                }
            }
        }
    }

    private void placeCards() {
        for (Seat s : table.getSeatList()) {
            for (BJHand h : s.getHandList()) {
                h.addCard(table.getDeck().drawCard());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ui.draw(table);
            }
        }
        table.getDealersHand().addCard(table.getDeck().drawCard());
        ui.draw(table);
    }

    private void placeBets() {
        for (int seatNo = 0; seatNo < table.getSeatList().length; seatNo++)
            if (table.getSeatList()[seatNo] != null) {
                Person player = table.getSeatList()[seatNo].getOwner();
                while (true)
                    try {
                        String answer = ui.ask(
                                "Player, " + player.getName() + " (" + player.getMoney() +
                                        "), please place your bet for seat number " + seatNo + ".");
                        BJHand tmpHand = new BJHand(player);
                        tmpHand.setBettingAmount(Integer.parseInt(answer));
                        table.getSeatList()[seatNo].clearHands();
                        table.getSeatList()[seatNo].addHand(tmpHand);
                        break;
                    } catch (Exception ignored) {
                    }
            }
    }

    private void sitPlayer(String playerName, int seatNumber) {
        if (table.getSeatList()[seatNumber] != null)
            throw new IllegalArgumentException("Seat is not empty.");
        if (player.containsKey(playerName))
            table.getSeatList()[seatNumber] = new Seat(player.get(playerName));
        else {
            Person tmpPerson = new Person(playerName);
            tmpPerson.increaseMoney(PLAYER_START_MONEY);
            table.getSeatList()[seatNumber] = new Seat(player.put(playerName, tmpPerson));
        }
    }

    private void freeSeat(int seatNumber) {
        if (table.getSeatList()[seatNumber] == null)
            throw new IllegalArgumentException("Seat is empty.");
        table.getSeatList()[seatNumber] = null;
    }
}
