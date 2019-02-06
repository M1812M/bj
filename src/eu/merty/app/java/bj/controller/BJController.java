package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.*;
import eu.merty.app.java.bj.view.BJCommandLineUI;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//import interfaces.Cardgame;

public class BJController {
    private final int NUMBER_OF_SEATS = 3;
    private final int NUMBER_OF_CARD_DECKS = 1;
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

    private String getHandOptions(BJHand h) {
        List<String> options = new LinkedList<String>();
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("hit");
        }
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("stand");
        }
        if (BJRuleset.mayDoubleDown(h)) {
            options.add("double");
        }
        if (BJRuleset.maySplit(h)) {
            options.add("split");
        }

        return String.join(", ", options);
    }

    public void doCommand(String command) {
        switch (command) {
            case "sit":
                String playerName = ui.ask("What is your name?");
                while (true) try {
                    sitPlayer(playerName, Integer.parseInt(ui.ask("On which seat?")));
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
        placeBets();
        placeCards();
        playHands();
        playDealer();
        payout();
    }

    private void payout() {

    }

    private void playDealer() {

    }

    private void playHands() {
        for (Seat s : table.getSeatList()) {
            for (BJHand h : s.getHandList()) {
                if (!BJRuleset.hasBlackJack(h)) {
                    do {
                        String answer = ui.ask("What do you, " + s.getOwner().getName() + ", want to do? " + getHandOptions(h));
                        // TODO do stuff
                    } while ( getHandOptions(h).contains(answer);
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
                        String answer = ui.ask("Player, " + player.getName() + ", please place your bet for seat number " + seatNo + ".");
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
