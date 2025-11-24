package ca.SumAwesomeGame.model.character;

/**
 * Represents an enemy character in the opponent team.
 * Each enemy has a location (LEFT, MIDDLE, or RIGHT), health, and attack strength.
 * Enemies attack the player character periodically or when the player makes a failed move.
 * 
 * @author Sum Awesome Game Team
 */
public class Enemy{
    private final int location;
    private int health = 150; // Increased for easier testing (survive longer)
    private final int attackStrength = 200; // Reduced for easier testing (less damage)
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
