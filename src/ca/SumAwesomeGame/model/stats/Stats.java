package ca.SumAwesomeGame.model.stats;

import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

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

    public void addUsedWeapon(Weapon currentWeapon) {
        if (currentWeapon != null && !(currentWeapon instanceof NullWeapon)) {
            // Only add if not already in the list
            if (!weaponsUsed.contains(currentWeapon)) {
                weaponsUsed.add(currentWeapon);
            }
        }
    }
    
    /**
     * Records damage received by the player
     * @param amount The amount of damage received
     */
    public void recordDamage(int amount) {
        damageReceived += amount;
    }

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

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
    
    // ========== Getters ==========
    
    public int getGamesWon() {
        return gamesWon;
    }
    
    public int getGamesLost() {
        return gamesLost;
    }
    
    public int getDamageReceived() {
        return damageReceived;
    }
    
    public int getTotalNumberOfFills() {
        return totalNumberOfFills;
    }
    
    public List<Weapon> getWeaponsUsed() {
        return new ArrayList<>(weaponsUsed); // Return copy to prevent external modification
    }
    
    public int getWeaponsUsedCount() {
        return weaponsUsed.size();
    }
}
