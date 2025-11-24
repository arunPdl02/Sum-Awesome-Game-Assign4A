package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.Game;

public class Enemy{
    private Game game;
    private final int location;
    private int health = 50;
    private final int attackStrength = 500;
    private Player player;

    public Enemy(int location, Player player) {
        this.location = location;
        this.player = player;
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

    public void reduceHealth(int damage){
        health -= damage;
        // Health cannot be negative
        if (health < 0) {
            health = 0;
        }
    }

    public void attack() {
        player.reduceHealth(attackStrength);
    }

}
