package eu.merty.app.java.bj.model;

public class Person {
    int money;
    private String name;

    public Person(String name) {
        this.name = name;
        this.money = 0;
    }

    public void increaseMoney(int amount) {
        this.money += amount > 0 ? amount : 0;
    }

    public int decreaseMoney(int amount) throws Exception {
        if (amount <= 0 || money < amount)
            // FIXME 1: Throw on invalid amounts (<=0 or exceeds funds) so callers can re-prompt without creating invalid bets.
            throw new Exception("This player doesn't hold enough money or the amount is negative.");
        else
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
