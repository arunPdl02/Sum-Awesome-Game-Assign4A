package ca.SumAwesomeGame.model.stats;

import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks game statistics including games won/lost, damage received, fills completed, and weapons used.
 * Implements GameObserver to automatically track statistics based on game events.
 * 
 * @author Sum Awesome Game Team
 */
public class Stats implements GameObserver {
    private int gamesWon;
    private int gamesLost;
    private int damageReceived;
    private int totalNumberOfFills;
    private List<Weapon> weaponsUsed = new ArrayList<>();
    private Game game;
    
    // Track previous fill state to detect when fill completes
    private boolean previousFillComplete = false;
    // Track previous match state to detect when match ends
    private boolean previousMatchWon = false;
    private boolean previousMatchLost = false;

    /**
     * Adds a weapon to the list of weapons used (if not already present and not NullWeapon).
     * @param currentWeapon The weapon to add to the used weapons list
     */
    public void addUsedWeapon(Weapon currentWeapon) {
        if (currentWeapon != null && !(currentWeapon instanceof NullWeapon)) {
            // Only add if not already in the list
            if (!weaponsUsed.contains(currentWeapon)) {
                weaponsUsed.add(currentWeapon);
            }
        }
    }
    
    /**
     * Records damage received by the player.
     * @param amount The amount of damage received
     */
    public void recordDamage(int amount) {
        damageReceived += amount;
    }

    /**
     * Updates statistics based on game state changes.
     * Tracks match wins/losses, fills completed, and weapons used.
     */
    @Override
    public void update() {
        if (game == null) {
            return;
        }
        
        // Track match won (only increment once per match)
        boolean currentMatchWon = game.isMatchWon();
        if (currentMatchWon && !previousMatchWon) {
            gamesWon++;
        }
        previousMatchWon = currentMatchWon;
        
        // Track match lost (only increment once per match)
        boolean currentMatchLost = game.isMatchLost();
        if (currentMatchLost && !previousMatchLost) {
            gamesLost++;
        }
        previousMatchLost = currentMatchLost;
        
        // Track fills completed (detect transition from incomplete to complete)
        boolean currentFillComplete = game.fillComplete();
        if (currentFillComplete && !previousFillComplete) {
            totalNumberOfFills++;
        }
        previousFillComplete = currentFillComplete;
        
        // Track weapons used (when player just attacked)
        if (game.didPlayerJustAttack()) {
            Weapon equippedWeapon = game.getPlayer().getEquippedWeapon();
            addUsedWeapon(equippedWeapon);
        }
        
        // Reset tracking when new game starts
        if (game.isStartNewGame()) {
            previousFillComplete = false;
            previousMatchWon = false;
            previousMatchLost = false;
        }
    }

    /**
     * Subscribes the stats tracker to game state changes.
     * @param game The game instance to observe
     */
    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
    
    // ========== Getters ==========
    
    /**
     * Gets the total number of games won.
     * @return Number of games won
     */
    public int getGamesWon() {
        return gamesWon;
    }
    
    /**
     * Gets the total number of games lost.
     * @return Number of games lost
     */
    public int getGamesLost() {
        return gamesLost;
    }
    
    /**
     * Gets the total damage received by the player.
     * @return Total damage received
     */
    public int getDamageReceived() {
        return damageReceived;
    }
    
    /**
     * Gets the total number of fills completed.
     * @return Number of fills completed
     */
    public int getTotalNumberOfFills() {
        return totalNumberOfFills;
    }
    
    /**
     * Gets a copy of the list of weapons used.
     * @return Copy of the weapons used list
     */
    public List<Weapon> getWeaponsUsed() {
        return new ArrayList<>(weaponsUsed); // Return copy to prevent external modification
    }
    
    /**
     * Gets the count of unique weapons used.
     * @return Number of unique weapons used
     */
    public int getWeaponsUsedCount() {
        return weaponsUsed.size();
    }
}
