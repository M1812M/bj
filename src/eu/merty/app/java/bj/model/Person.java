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

    public int decreaseMoney(int amount) {
        if (amount < 0 || money - amount < 0)
            try {
                throw new Exception("This player doesn't hold enough money or the amount is negative.");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
