package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.AttackResult;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.List;

/**
 * UI component for displaying game statistics.
 * Automatically displays stats when a match ends (win or loss).
 * Also provides a static method for manual stats display.
 *
 * @author Sum Awesome Game Team
 */
public class StatsUI implements GameObserver {
    private Game game;
    private boolean previousMatchWon = false;
    private boolean previousMatchLost = false;
    private Stats stats;

    public StatsUI(Stats stats) {
        this.stats = stats;
    }

    @Override
    public void update() {
        if (game == null) {
            return;
        }

        // Display stats when match ends (only once per match)
        boolean currentMatchWon = game.isMatchWon();
        boolean currentMatchLost = game.isMatchLost();

        if ((currentMatchWon && !previousMatchWon) || (currentMatchLost && !previousMatchLost)) {
            displayStats();
        }

        previousMatchWon = currentMatchWon;
        previousMatchLost = currentMatchLost;

        // Reset tracking when new game starts
        if (game.isStartNewGame()) {
            previousMatchWon = false;
            previousMatchLost = false;
        }

        if (game.didPlayerJustAttack()) {
            printAttackStats();
        }
    }

    private void printAttackStats() {
        AttackResult result = game.getLastAttackResult();
        System.out.println("Fill complete! Strength is " + result.getBaseDamage());
        Weapon weapon = stats.lastUsedWeapon;
        List<Ring> rings = stats.lastUsedRings;
        if (!rings.isEmpty()) {
            for(Ring ring: rings){
                System.out.println(ring.getName() + " adds " + ring.getAbility());
            }
        }
        if (!(weapon instanceof NullWeapon)) {
            System.out.println(weapon.getName() + " targets ");
        }
    }

    /**
     * Public static method to display stats - can be called from anywhere
     *
     * @param game The game instance to get stats from
     */
    public static void displayStats(Game game) {
        var stats = game.getStats();

        System.out.println("\n=== Game Statistics ===");
        System.out.println("Games Won: " + stats.getGamesWon());
        System.out.println("Games Lost: " + stats.getGamesLost());
        System.out.println("Total Damage Received: " + stats.getDamageReceived());
        System.out.println("Total Fills Completed: " + stats.getTotalNumberOfFills());

        System.out.println("\nWeapons Used (" + stats.getWeaponsUsedCount() + "):");
        List<Weapon> weaponsUsed = stats.getWeaponsUsed();
        if (weaponsUsed.isEmpty()) {
            System.out.println("  (No weapons used yet)");
        } else {
            for (Weapon weapon : weaponsUsed) {
                System.out.println("  - " + weapon.getName());
            }
        }
        System.out.println("======================\n");
    }

    /**
     * Private instance method for automatic display on match end
     */
    private void displayStats() {
        displayStats(game); // Call the static method
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
