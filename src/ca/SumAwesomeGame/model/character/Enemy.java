package ca.SumAwesomeGame.model.character;

public class Enemy {
    private final int location;
    private int health = 50;

    public Enemy(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
