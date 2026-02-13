package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.*;
import eu.merty.app.java.bj.view.BJCommandLineUI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO import interfaces.Cardgame;

public class BJController {
    private static final int DEFAULT_NUMBER_OF_SEATS = 5;
    private static final int NUMBER_OF_CARD_DECKS = 4;
    private static final int DEFAULT_PLAYER_START_MONEY = 20;
    private static final int NUMBER_BLACKJACK = 21;
    private static final int NUMBER_DEALER_STAND = 17;
    private static final int DEFAULT_DEAL_DELAY_MS = 500;

    private int numberOfSeats = DEFAULT_NUMBER_OF_SEATS;
    private int playerStartMoney = DEFAULT_PLAYER_START_MONEY;
    private int dealDelayMs = DEFAULT_DEAL_DELAY_MS;

    private BJTable table = new BJTable(numberOfSeats, NUMBER_OF_CARD_DECKS);
    private final Map<String, Person> players = new HashMap<>();
    private final BJCommandLineUI ui = new BJCommandLineUI();

    public BJController() {
        ui.setDrawDelayMs(dealDelayMs);
    }

    public static void main(String[] args) {
        BJController bjController = new BJController();
        bjController.run();
    }

    public void run() {
        startOrConfigure();
        do {
            String userAction = ui.ask("These are your options: " + getRoundOptions());
            if (getRoundOptions().contains(userAction))
                doCommand(userAction);
        } while (table.getOccupiedSeatsNumber() > 0);
    }

    private void startOrConfigure() {
        try {
            String answer = normalizeInput(ui.ask("Start or configure? (start/configure)"));
            if ("configure".equals(answer)) {
                configureGame();
            }
        } catch (Exception ignored) {
        }
    }

    private void configureGame() {
        numberOfSeats = readOptionalInt(
                "Number of seats? (Enter for default " + numberOfSeats + ")",
                numberOfSeats,
                1
        );
        playerStartMoney = readOptionalInt(
                "Start money per player? (Enter for default " + playerStartMoney + ")",
                playerStartMoney,
                1
        );
        dealDelayMs = readOptionalInt(
                "Deal delay in ms? (Enter for default " + dealDelayMs + ")",
                dealDelayMs,
                0
        );

        ui.setDrawDelayMs(dealDelayMs);
        table = new BJTable(numberOfSeats, NUMBER_OF_CARD_DECKS);
    }

    private int readOptionalInt(String prompt, int fallback, int minValue) {
        try {
            String answer = ui.ask(prompt);
            if (answer == null || answer.trim().isEmpty()) {
                return fallback;
            }

            int value = Integer.parseInt(answer.trim());
            return value >= minValue ? value : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private List<String> getRoundOptions() {
        List<String> options = new ArrayList<>();
        int occupiedSeats = table.getOccupiedSeatsNumber();
        if (occupiedSeats < NUMBER_OF_SEATS) {
            options.add("sit");
        }
        if (occupiedSeats > 0) {
            options.add("up");
            options.add("run");
        }
        return options;
    }

    private List<String> getHandOptions(BJHand h, Person p) {
        List<String> options = new LinkedList<String>();
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("hit");
        }
        if (BJRuleset.mayHitAndStand(h)) {
            options.add("stand");
        }
        if (p.getMoney() >= h.getBetValue() && BJRuleset.mayDoubleDown(h)) {
            options.add("double");
        }
        if (p.getMoney() >= h.getBetValue() && BJRuleset.maySplit(h)) {
            options.add("split");
        }

        return options;
    }

    public void doCommand(String command) {
        switch (normalizeInput(command)) {
            case "sit":
                String playerName = ui.ask("What is your name?");
                while (true) try { // TODO show which seats are occupied with whom.
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
                        UI.err("Player is not occupied.");
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

    private int parseSeatNumber(String rawSeatNumber) {
        int seatNumber = Integer.parseInt(rawSeatNumber.trim()) - 1;
        if (!isValidSeatIndex(seatNumber)) {
            throw new IllegalArgumentException("Seat number out of bounds.");
        }
        return seatNumber;
    }

    private boolean isValidSeatIndex(int seatNumber) {
        return seatNumber >= 0 && seatNumber < table.getSeatList().length;
    }

    private void playRound() {
        placeBets();
        dealCards();
        playHands();
        playDealer();
        payout();
        game.cleanRound();
    }

    private void payout() {
        int dealersHandValue = BJRuleset.getHandValue(table.getDealer().getHand());
        for (Seat s : table.getSeatList()) {
            for (BJHand h : s.getHandList()) {
                int playersHandValue = BJRuleset.getHandValue(h);

                // TODO implement an instant BJ with 50% extra.
                if (playersHandValue > 21) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " with value " + BJRuleset.getHandValue(h) + " lost.");
                } else if (dealersHandValue > 21) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " with value " + BJRuleset.getHandValue(h) + " won.");
                    s.getOwner().increaseMoney(h.getBetValue() * 2);
                } else if (dealersHandValue == playersHandValue) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " with value " + BJRuleset.getHandValue(h) + " has a push.");
                    s.getOwner().increaseMoney(h.getBetValue());
                } else if (dealersHandValue < playersHandValue) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " with value " + BJRuleset.getHandValue(h) + " won.");
                    s.getOwner().increaseMoney(h.getBetValue() * 2);
                } else { //if (dealerV > playerV) {
                    ui.message(s.getOwner().getName() + ", your hand " + h + " with value " + BJRuleset.getHandValue(h) + " lost.");
                }
            }
        }
    }

    private void playDealer() {
        do {
            table.getDealersHand().addCard(table.getDeck().drawCard());
            ui.draw(table);
        } while (BJRuleset.getHandValue(table.getDealersHand()) < 17);
        ui.message("Dealer has " + table.getDealersHand() + " with value " + BJRuleset.getHandValue(table.getDealersHand()) + ".");
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
                    if (getHandOptions(h, s.getOwner()).contains(answer)) {
                        switch (answer) {
                            case "hit":
                                h.addCard(table.getDeck().drawCard());
                                break;
                            case "stand":
                                done = true;
                                break;
                            case "double":
                                h.addBetValue(s.getOwner().decreaseMoney(h.getBetValue()));
                                h.addCard(table.getDeck().drawCard());
                                done = true;
                                break;
                            case "split":
                                BJHand tmpH = new BJHand(s.getOwner());
                                tmpH.setBetValue(s.getOwner().decreaseMoney(h.getBetValue()));
                                tmpH.addCard(h.removeCard(0));
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
        if (!placePlayerCards()) {
            return;
        }
        table.getDealer().getHand().addCard(table.getDeck().drawCard());
        ui.draw(table);
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        placePlayerCards();
    }

    private boolean placePlayerCards() {
        for (Seat seat : table.getSeatList()) {
            for (BJHand hand : seat.getHandList()) {
                hand.addCard(table.getDeck().drawCard());
                ui.draw(table);
                if (Thread.currentThread().isInterrupted()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeBets() {
        for (int seatNo = 0; seatNo < table.getSeatList().length; seatNo++)
            if (!table.getSeatList()[seatNo].isEmpty()) {
                Person player = table.getSeatList()[seatNo].getOwner();
                while (true)
                    try {
                        String answer = ui.ask(
                                "Player, " + player.getName() + " (" + player.getMoney() +
                                        "), please place your bet for seat number " + (seatNo + 1) + ".");
                        BJHand tmpHand = new BJHand(player);
                        tmpHand.setBetValue(player.decreaseMoney(Integer.parseInt(answer)));
                        table.getSeatList()[seatNo].clearHands();
                        table.getSeatList()[seatNo].addHand(tmpHand);
                        break;
                    } catch (Exception ignored) {
                    }
            }
    }

    private void sitPlayer(String playerName, int seatNumber) {
        if (!table.getSeatList()[seatNumber].isEmpty())
            throw new IllegalArgumentException("Seat is not empty.");
        if (player.containsKey(playerName))
            table.getSeatList()[seatNumber].sitOwner(player.get(playerName));
        else {
            Person tmpPerson = new Person(playerName);
            tmpPerson.increaseMoney(PLAYER_START_MONEY);
            player.put(playerName, tmpPerson);
            ui.message("Welcome " + tmpPerson.getName());
            table.getSeatList()[seatNumber].sitOwner(tmpPerson);
        }
    }

    private void freeSeat(int seatNumber) {
        if (table.getSeatList()[seatNumber].isEmpty())
            throw new IllegalArgumentException("Seat is empty.");
        table.getSeatList()[seatNumber].freeOwner();
    }

    /**
     * Return a compact description of all seats: "Seat 1: empty, Seat 2: Alice, ..."
     */
    private String getSeatStatus() {
        StringBuilder sb = new StringBuilder();
        Seat[] seats = table.getSeatList();
        for (int i = 0; i < seats.length; i++) {
            sb.append("Seat ").append(i + 1).append(": ");
            if (seats[i].isEmpty()) {
                sb.append("empty");
            } else {
                sb.append(seats[i].getOwner().getName());
            }
            if (i < seats.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private String normalizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().toLowerCase(Locale.ROOT);
    }
}
