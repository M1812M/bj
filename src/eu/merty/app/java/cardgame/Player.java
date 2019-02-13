package eu.merty.app.java.cardgame;

public abstract class Player {
    public abstract String getName();
/*    private Person person;

    public Player(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        if (person == null)
            return "";
        return person.getName() + " (" + handList.stream().map(Object::toString).collect(Collectors.joining(", ", "[", "]")) + ")";
    }
*/
}
