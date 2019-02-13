package eu.merty.app.java.bj.model;

import eu.merty.app.java.cardgame.Player;

public class Person extends Player {
    private int money;
    private String name;

    public Person(String name) {
        this.name = name;
        this.money = 0;
    }

    public void changeMoneyByDelta(int amount) {
        if (this.money + amount < 0)
            throw new IllegalArgumentException("Money value cannot be negative.");
        this.money += amount;
    }

    public int getMoney() {
        return this.money;
    }

    public String getName() {
        return this.name;
    }
}
