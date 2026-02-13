package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.BJHand;
import eu.merty.app.java.bj.model.BJRuleset;
import eu.merty.app.java.bj.model.BJTable;
import eu.merty.app.java.bj.model.Person;
import eu.merty.app.java.bj.model.Seat;
import eu.merty.app.java.bj.view.BJCommandLineUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
            List<String> roundOptions = getRoundOptions();
            try {
                String userAction = normalizeInput(ui.ask("These are your options: " + String.join(", ", roundOptions)));
                if (roundOptions.contains(userAction)) {
                    doCommand(userAction);
                } else {
                    ui.err("Could not find the command.");
                }
            } catch (Exception e) {
                ui.err("Sorry, that input is not valid.");
            }
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
        if (occupiedSeats < numberOfSeats) {
            options.add("sit");
        }
        if (occupiedSeats > 0) {
            options.add("up");
            options.add("run");
        }
        return options;
    }

    private List<String> getHandOptions(BJHand hand, Person player) {
        List<String> options = new ArrayList<>();
        boolean mayHitOrStand = BJRuleset.mayHitAndStand(hand);
        if (mayHitOrStand) {
            options.add("hit");
            options.add("stand");
        }
        if (player.getMoney() >= hand.getBetValue() && BJRuleset.mayDoubleDown(hand)) {
            options.add("double");
        }
        if (player.getMoney() >= hand.getBetValue() && BJRuleset.maySplit(hand)) {
            options.add("split");
        }

        return options;
    }

    public void doCommand(String command) {
        switch (normalizeInput(command)) {
            case "sit":
                String playerName = ui.ask("What is your name?");
                while (true) {
                    try {
                        String seatPrompt = "On which of the " + numberOfSeats + " seat(s)? Current: " + getSeatStatus();
                        int seatNumber = parseSeatNumber(ui.ask(seatPrompt));
                        sitPlayer(playerName, seatNumber);
                        break;
                    } catch (Exception ignored) {
                        ui.err("Invalid seat number.");
                    }
                }
                break;
            case "up":
                while (true) {
                    try {
                        int seatNumber = parseSeatNumber(ui.ask("Which seat should be freed?"));
                        freeSeat(seatNumber);
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
                ui.err("Could not find the command.");
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
        placeCards();
        playHands();
        playDealer();
        payout();
        cleanTable();
    }

    private void cleanTable() {
        if (table.getDeck().getDeckSize() < 52 * NUMBER_OF_CARD_DECKS / 2) {
            table.newDeck(NUMBER_OF_CARD_DECKS);
        }
        table.getDealer().removeHand();
    }

    private void payout() {
        int dealerHandValue = BJRuleset.getHandValue(table.getDealer().getHand());
        for (Seat seat : table.getSeatList()) {
            for (BJHand hand : seat.getHandList()) {
                int playerHandValue = BJRuleset.getHandValue(hand);
                String handMessage = seat.getOwner().getName() + ", your hand " + hand + " with value " + playerHandValue;

                if (playerHandValue > NUMBER_BLACKJACK) {
                    ui.message(handMessage + " lost.");
                } else if (dealerHandValue > NUMBER_BLACKJACK || playerHandValue > dealerHandValue) {
                    ui.message(handMessage + " won.");
                    seat.getOwner().increaseMoney(hand.getBetValue() * 2);
                } else if (dealerHandValue == playerHandValue) {
                    ui.message(handMessage + " has a push.");
                    seat.getOwner().increaseMoney(hand.getBetValue());
                } else {
                    ui.message(handMessage + " lost.");
                }
            }
        }
    }

    private void playDealer() {
        while (BJRuleset.getHandValue(table.getDealer().getHand()) < NUMBER_DEALER_STAND) {
            table.getDealer().getHand().addCard(table.getDeck().drawCard());
            ui.draw(table);
        }
        ui.message("Dealer has " + table.getDealer().getHand() + " with value "
                + BJRuleset.getHandValue(table.getDealer().getHand()) + ".");
    }

    private void playHands() {
        for (Seat seat : table.getSeatList()) {
            for (int handIndex = 0; handIndex < seat.getHandList().size(); handIndex++) {
                boolean done = false;
                while (!done) {
                    BJHand currentHand = seat.getHandList().get(handIndex);
                    if (BJRuleset.hasBlackJack(currentHand)) {
                        break;
                    }

                    List<String> options = getHandOptions(currentHand, seat.getOwner());
                    if (options.isEmpty()) {
                        break;
                    }

                    String answer = normalizeInput(ui.ask(
                            "What do you, " + seat.getOwner().getName() +
                                    ", want to do for your hand (" + currentHand + ")? " +
                                    String.join(", ", options)
                    ));

                    if (!options.contains(answer)) {
                        ui.err("Sorry, that input is not valid.");
                        continue;
                    }

                    try {
                        switch (answer) {
                            case "hit":
                                currentHand.addCard(table.getDeck().drawCard());
                                break;
                            case "stand":
                                done = true;
                                break;
                            case "double":
                                currentHand.addBetValue(
                                        seat.getOwner().decreaseMoney(currentHand.getBetValue())
                                );
                                currentHand.addCard(table.getDeck().drawCard());
                                done = true;
                                break;
                            case "split":
                                BJHand splitHand = new BJHand(seat.getOwner());
                                splitHand.setBetValue(seat.getOwner().decreaseMoney(currentHand.getBetValue()));
                                splitHand.addCard(currentHand.removeCard(0));
                                seat.addHand(splitHand);

                                currentHand.addCard(table.getDeck().drawCard());
                                splitHand.addCard(table.getDeck().drawCard());
                                break;
                            default:
                                ui.err("Unknown hand action.");
                                break;
                        }
                        ui.draw(table);
                    } catch (Exception e) {
                        ui.err("Sorry, that input is not valid.");
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
        for (int seatNo = 0; seatNo < table.getSeatList().length; seatNo++) {
            if (table.getSeatList()[seatNo].isEmpty()) {
                continue;
            }

            Person player = table.getSeatList()[seatNo].getOwner();
            while (true) {
                try {
                    String answer = ui.ask(
                            "Player, " + player.getName() + " (" + player.getMoney()
                                    + "), please place your bet for seat number " + (seatNo + 1) + "."
                    );
                    BJHand hand = new BJHand(player);
                    hand.setBetValue(player.decreaseMoney(Integer.parseInt(answer.trim())));
                    table.getSeatList()[seatNo].clearHands();
                    table.getSeatList()[seatNo].addHand(hand);
                    break;
                } catch (Exception ignored) {
                    ui.err("Invalid bet amount.");
                }
            }
        }
    }

    private void sitPlayer(String playerName, int seatNumber) {
        if (!isValidSeatIndex(seatNumber)) {
            throw new IllegalArgumentException("Seat number out of bounds.");
        }
        if (table.getSeatList()[seatNumber].isEmpty()) {
            if (playerName == null || playerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Player name is required.");
            }

            String trimmedName = playerName.trim();
            if (players.containsKey(trimmedName)) {
                table.getSeatList()[seatNumber].sitOwner(players.get(trimmedName));
            } else {
                Person newPlayer = new Person(trimmedName);
                newPlayer.increaseMoney(playerStartMoney);
                players.put(trimmedName, newPlayer);
                ui.message("Welcome " + newPlayer.getName());
                table.getSeatList()[seatNumber].sitOwner(newPlayer);
            }
            return;
        }
        throw new IllegalArgumentException("Seat is not empty.");
    }

    private void freeSeat(int seatNumber) {
        if (!isValidSeatIndex(seatNumber)) {
            throw new IllegalArgumentException("Seat number out of bounds.");
        }
        if (table.getSeatList()[seatNumber].isEmpty()) {
            throw new IllegalArgumentException("Seat is empty.");
        }
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
