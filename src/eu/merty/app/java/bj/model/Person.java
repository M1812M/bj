package eu.merty.app.java.bj.model;

public class Person {
    private int money;
    private final String name;

    public Person(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must not be empty.");
        }
        this.name = name.trim();
        this.money = 0;
    }

    public void increaseMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount is negative.");
        }
        this.money += amount;
    }

    public int decreaseMoney(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive.");
        }
        if (money < amount) {
            throw new IllegalStateException("This player doesn't hold enough money.");
        }

        this.money -= amount;
        return amount;
    }

    public int getMoney() {
        return this.money;
    }

    public String getName() {
        return this.name;
    }
}
