package eu.merty.app.java.bj.view;

import eu.merty.app.java.bj.model.BJHand;
import eu.merty.app.java.bj.model.BJRuleset;
import eu.merty.app.java.bj.model.BJTable;
import eu.merty.app.java.bj.model.Seat;
import eu.merty.app.java.bj.model.Table;

import java.util.Scanner;

public class BJCommandLineUI {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int DEFAULT_DRAW_DELAY_MS = 500;

    private int drawDelayMs = DEFAULT_DRAW_DELAY_MS;

    public String ask(String question) {
        message(question);
        return SCANNER.hasNextLine() ? SCANNER.nextLine() : "";
    }

    public void message(String message) {
        System.out.println(message);
    }

    public void err(String message) {
        System.err.println(message);
    }

    public void setDrawDelayMs(int drawDelayMs) {
        if (drawDelayMs < 0) {
            throw new IllegalArgumentException("drawDelayMs must be >= 0.");
        }
        this.drawDelayMs = drawDelayMs;
    }

    public void draw(Table table) {
        pauseBeforeDraw();

        if (table instanceof BJTable) {
            drawBJTable((BJTable) table);
            return;
        }
        message(table.toString());
    }

    private void drawBJTable(BJTable table) {
        message("=== Blackjack Table ===");
        message("Dealer: " + table.getDealer().getHand() + " (value "
                + BJRuleset.getHandValue(table.getDealer().getHand()) + ")");

        Seat[] seats = table.getSeatList();
        for (int i = 0; i < seats.length; i++) {
            message("Seat " + (i + 1) + ": " + describeSeat(seats[i]));
        }
    }

    private String describeSeat(Seat seat) {
        if (seat.isEmpty()) {
            return "empty";
        }
        if (seat.getHandList().isEmpty()) {
            return seat.getOwner().getName() + " (money " + seat.getOwner().getMoney() + ", no hand)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(seat.getOwner().getName())
                .append(" (money ")
                .append(seat.getOwner().getMoney())
                .append("): ");

        for (int i = 0; i < seat.getHandList().size(); i++) {
            BJHand hand = seat.getHandList().get(i);
            if (i > 0) {
                sb.append(" | ");
            }
            sb.append(hand).append(" value ").append(BJRuleset.getHandValue(hand));
        }
        return sb.toString();
    }

    private void pauseBeforeDraw() {
        if (drawDelayMs == 0) {
            return;
        }

        try {
            Thread.sleep(drawDelayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
