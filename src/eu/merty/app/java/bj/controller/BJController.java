package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.Card;
import eu.merty.app.java.bj.model.Deck;

//import interfaces.Cardgame;

import eu.merty.app.java.bj.model.BJHand;
import eu.merty.app.java.bj.model.BJTable;
import eu.merty.app.java.bj.model.Person;
import eu.merty.app.java.bj.model.Seat;
import eu.merty.app.java.bj.view.BJCommandLineUI;

import java.util.HashMap;

public class BJController {
    private final int NUMBER_OF_SEATS = 3;
    private final int NUMBER_OF_CARD_DECKS = 1;
    private final int PLAYER_START_MONEY = 20;
    private final String[] options = new String[]
            {"sit", "up", "run", "hit", "stand", "doubleD", "split"};
    private BJTable table;
    private HashMap<String, Person> player;
    //  private CardGameUI ui;
    private BJCommandLineUI ui;
    // 0 sit - 1 up - 2 run - 3 hit - 4 stand - 5 doubleD - 6 split

    public BJController() {
        table = new BJTable(NUMBER_OF_SEATS, NUMBER_OF_CARD_DECKS);
        player = new HashMap<>();
        ui = new BJCommandLineUI(this);
    }

    public void doCommand(String[] command) {
        if (command == null || command.length == 0)
            throw new IllegalArgumentException();
        switch (command[0]) {
            case "sit":
                String playerName = ui.ask("What is your name?");
                while (true) try {
                    sitPlayer(playerName, Integer.parseInt(ui.ask("On which seat?")));
                    break;
                } catch (Exception ignored) {
                    ui.message("Invalid seat number.");
                }
                break;
            case "up":
                while (true) {
                    try {
                        freeSeat(Integer.parseInt(ui.ask("Which seat should be freed?")));
                        break;
                    } catch (Exception ignored) {
                        ui.message("Seat is not occupied.");
                    }
                }
                break;
            case "run":
                break;
            case "hit":
                break;
            case "stand":
                break;
            case "doubleD":
                break;
            case "split":
                break;
            default:
                throw new
                        IllegalArgumentException("Could not found the command.");
        }
    }

    private void payout() {

    }

    private void playDealer() {

    }

    private void playHands() {

    }

    private void placeCards() {

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
                        table.getSeatList()[seatNo].getHandList().add(tmpHand);
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
