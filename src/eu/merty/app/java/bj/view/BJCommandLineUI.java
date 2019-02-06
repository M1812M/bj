package eu.merty.app.java.bj.view;

import eu.merty.app.java.bj.controller.BJController;
import eu.merty.app.java.bj.model.Table;

import java.util.Scanner;

public class BJCommandLineUI {
    private BJController game;
    private Scanner scanner;
/*
    private String options;

    public BJCommandLineUI(BJController game) {
        this.game = game;
        this.options = "sit";
    }

    public void run() {
        String userAction = ask("These are your options: " + options);
        game.doCommand(new String[]{userAction});
    }

    public void action(List<String> optionsList) {
    }

    public boolean paint(String[][] tableData) {
        for (String[] stringArray : tableData) {
            switch (stringArray[0]) {
                case "BJTable":
                    message("BlackJack Table");
                    break;
                case "Dealer":
                    message("Dealer: " + stringArray[1]);
                    break;
                case "Seat":
                    String hands = "";
                    for (int i = 3; i + 1 < stringArray.length; i++) {
                        hands += ", <" + stringArray[i] + ">(" + stringArray[i + 1] + ")";
                    }
                    message(stringArray[1] + "(" + stringArray[2] + " M):" + (hands.length() > 0 ? hands.substring(1) : ""));
                    break;
                case "Options":
                    options = "";
                    for (int i = 0; i + 1 < stringArray.length; i++) {
                        options += ", " + stringArray[i];
                    }
                    options = options.length() > 1 ? options.substring(2) : "";
            }
        }
        return true;
    }
*/

    public String ask(String question) {
        message(question);
        String answer = "";
        if (scanner.hasNextLine())
            answer = scanner.nextLine();
        return answer;
    }

    public void message(String message) {
        System.out.println(message);
    }

    public void err(String message) {
        System.err.println(message);
    }

    public void draw(Table t) {
        message(t.toString());
    }
}
