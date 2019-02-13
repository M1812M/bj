package eu.merty.app.java.bj.controller;

import eu.merty.app.java.bj.model.BJCardgame;
import eu.merty.app.java.bj.model.BJHand;
import eu.merty.app.java.bj.model.BJRuleset;
import eu.merty.app.java.bj.model.Person;
import eu.merty.app.java.bj.view.BJCommandLineUI;
import eu.merty.app.java.cardgame.Hand;
import eu.merty.app.java.cardgame.Seat;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// TODO import interfaces.Cardgame;

public class BJController {
    private final int NUMBER_OF_SEATS = 5;
    private final int NUMBER_OF_CARD_DECKS = 4;
    private final int PLAYER_START_MONEY = 20;
    private final String[] options = new String[]
            {"sit", "up", "run", "hit", "stand", "double", "split"};
    public BJCardgame game;
    private HashMap<String, Person> personList;
    //  private CardGameUI ui;
    private BJCommandLineUI ui;
    // 0 sit - 1 up - 2 run - 3 hit - 4 stand - 5 doubleD - 6 split

    public BJController() {
        game = new BJCardgame(NUMBER_OF_SEATS, NUMBER_OF_CARD_DECKS);
        personList = new HashMap<>();
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
        } while (game.getOccupiedSeatsNumber() > 0);
    }

    @NotNull
    private String getRoundOptions() {
        List<String> options = new LinkedList<String>();
        int occupiedSeats = game.getOccupiedSeatsNumber();
        if (occupiedSeats < NUMBER_OF_SEATS) {
            options.add("sit");
        }
        if (occupiedSeats > 0) {
            options.add("up");
        }
        if (occupiedSeats > 0) {
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
        if (p.getMoney() >= h.getBetAmount() && BJRuleset.mayDoubleDown(h)) {
            options.add("double");
        }
        if (p.getMoney() >= h.getBetAmount() && BJRuleset.maySplit(h)) {
            options.add("split");
        }

        return options;
    }

    public void doCommand(String command) {
        switch (command) {
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
                        freeSeat(Integer.parseInt(ui.ask("Which seat should be freed?")) - 1);
                        break;
                    } catch (Exception ignored) {
                        ui.err("Player is not occupied.");
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
        dealCards();
        playHands();
        playDealer();
        payout();
        game.resetGame();
    }

    private void payout() {
        int dealersHandValue = BJRuleset.getHandValue(game.getDealersHand());

        for (Seat seat : game.getSeatList()) {
            for (Hand hand : seat.getHandList()) {
                BJHand bjhand = (BJHand) hand;
                int playersHandValue = BJRuleset.getHandValue(hand);

                // TODO implement an instant BJ with 50% extra.
                if (playersHandValue > 21) {                        // LOST
                    ui.message(new StringBuilder()
                            .append(seat.getPlayersName())
                            .append(", your hand ")
                            .append(bjhand)
                            .append(" with value ")
                            .append(BJRuleset.getHandValue(hand))
                            .append(" lost.")
                            .toString());
                } else if (dealersHandValue > 21) {                 // WON
                    ui.message(new StringBuilder()
                            .append(seat.getPlayersName())
                            .append(", your hand ")
                            .append(bjhand)
                            .append(" with value ")
                            .append(BJRuleset.getHandValue(hand))
                            .append(" won.")
                            .toString());
                    personList.get(seat.getPlayersName()).changeMoneyByDelta(bjhand.getBetAmount() * 2);
                } else if (dealersHandValue == playersHandValue) {  // PUSH
                    ui.message(new StringBuilder()
                            .append(seat.getPlayersName())
                            .append(", your hand ")
                            .append(bjhand)
                            .append(" with value ")
                            .append(BJRuleset.getHandValue(hand))
                            .append(" has a push.")
                            .toString());
                    personList.get(seat.getPlayersName()).changeMoneyByDelta(bjhand.getBetAmount());
                } else if (dealersHandValue < playersHandValue) {   // WON
                    ui.message(new StringBuilder()
                            .append(seat.getPlayersName())
                            .append(", your hand ")
                            .append(bjhand)
                            .append(" with value ")
                            .append(BJRuleset.getHandValue(hand))
                            .append(" won.")
                            .toString());
                    personList.get(seat.getPlayersName()).changeMoneyByDelta(bjhand.getBetAmount() * 2);
                } else { //if (dealerV > playerV) {                 // LOST
                    ui.message(new StringBuilder()
                            .append(seat.getPlayersName())
                            .append(", your hand ")
                            .append(bjhand)
                            .append(" with value ")
                            .append(BJRuleset.getHandValue(hand))
                            .append(" lost.")
                            .toString());
                }
            }
        }
    }

    private void playDealer() {
        do {
            game.dealDealer();
            ui.draw(game);
        } while (BJRuleset.getHandValue(game.getDealersHand()) < 17);
        ui.message(new StringBuilder()
                .append("Dealer has ")
                .append(game.getDealersHand())
                .append(" with value ")
                .append(BJRuleset.getHandValue(game.getDealersHand()))
                .append(".")
                .toString());
    }

    private void playHands() {
        for (Seat seat : game.getSeatList()) {
            for (Hand hand : seat.getHandList()) {
                BJHand bjHand = (BJHand) hand;
                boolean done = false;
                while (!BJRuleset.hasBlackJack(bjHand) && getHandOptions(bjHand, this.personList.get(seat.getPlayersName())).size() > 0 && !done) {
                    String answer = ui.ask(
                            "What do you, " +
                                    seat.getPlayersName() +
                                    ", want to do for your hand (" + bjHand + ")? " +
                                    String.join(
                                            ", ",
                                            getHandOptions(
                                                    bjHand,
                                                    this.personList.get(seat.getPlayersName()))));
                    if (getHandOptions(bjHand, this.personList.get(seat.getPlayersName())).contains(answer)) {
                        switch (answer) {
                            case "hit":
                                game.dealCard(bjHand);
                                break;
                            case "stand":
                                done = true;
                                break;
                            case "double":
                                this.personList
                                        .get(seat.getPlayersName())
                                        .changeMoneyByDelta(0 - bjHand.getBetAmount());
                                bjHand.changeBetAmountByDelta(bjHand.getBetAmount());
                                game.dealCard(bjHand);
                                done = true;
                                break;
                            case "split":
                                BJHand tmpH = new BJHand(bjHand.getBetAmount());
                                this.personList
                                        .get(seat.getPlayersName())
                                        .changeMoneyByDelta(0 - bjHand.getBetAmount());
                                seat.addHand(tmpH); // to debug, if taking for next hand to play
                                break;
                            default:
                                ui.message("Try again!");
                                break;
                        }
                        ui.draw(game);
                    }
                }
            }
        }
    }

    private void dealCards() {
        game.dealCards();
        ui.draw(game);
        game.dealDealer();
        ui.draw(game);
        game.dealCards();
        ui.draw(game);
    }

    private void placeBets() {
        for (int seatNo = 0; seatNo < game.getSeatList().length; seatNo++) {
            if (!game.getSeatList()[seatNo].isEmpty()) {
                Person player = personList.get(game.getSeatList()[seatNo].getPlayersName());
                while (true) {
                    try {
                        String answer = ui.ask(
                                "Player, " + player.getName() + " (" + player.getMoney() +
                                        "), please place your bet for seat number " + (seatNo + 1) + ".");
                        player.changeMoneyByDelta(0 - Integer.parseInt(answer));
                        BJHand tmpHand = new BJHand(Integer.parseInt(answer));
                        game.getSeatList()[seatNo].addHand(tmpHand);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void sitPlayer(String playerName, int seatNumber) {
        if (!game.getSeatList()[seatNumber].isEmpty())
            throw new IllegalArgumentException("Player is not empty.");
        if (!personList.containsKey(playerName)) {
            Person tmpPerson = new Person(playerName);
            tmpPerson.changeMoneyByDelta(PLAYER_START_MONEY);
            personList.put(playerName, tmpPerson);
        }
        game.sitPlayer(personList.get(playerName), seatNumber);
        ui.message("Welcome " + playerName);
    }

    private void freeSeat(int seatNumber) {
        if (game.getSeatList()[seatNumber].isEmpty())
            throw new IllegalArgumentException("Player is empty.");
        game.getSeatList()[seatNumber].leaveSeat();
    }
}
