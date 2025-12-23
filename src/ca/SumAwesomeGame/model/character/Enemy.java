package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.CellPosition;

public class Enemy{
    private final CellPosition location;
    private int health = 50;

    public int getAttackStrength() {
        return attackStrength;
    }

    private final int attackStrength = 10;

    public Enemy(CellPosition location) {
        this.location = location;
    }

    public CellPosition getLocation() {
        return location;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth(int damage){
        health -= damage;
    }

}
