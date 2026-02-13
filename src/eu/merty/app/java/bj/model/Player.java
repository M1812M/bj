package eu.merty.app.java.bj.model;

public class Player {
    private String name;
    private int money;

    public Player(String name) {
        this.name = name;
        this.money = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    @Override
    public String toString() {
        return name + "(" + money + ")";
    }
}
