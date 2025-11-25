package ca.SumAwesomeGame.UI.commands;

import ca.SumAwesomeGame.UI.StatsUI;
import ca.SumAwesomeGame.model.equipment.EquipmentFactory;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;

/**
 * Handles cheat commands for testing and debugging purposes.
 * Provides commands to manipulate game state, equipment, and player stats.
 * 
 * @author Sum Awesome Game Team
 */
public class Cheat {
    private static Game game;
    private static final int LOW_ENEMY_CHEAT_HEALTH = 1;

    /**
     * Sets the game instance for cheat commands to operate on.
     * @param gameInstance The game instance
     */
    public static void setGame(Game gameInstance) {
        game = gameInstance;
    }

    /**
     * Handles cheat command input and routes to appropriate handler.
     * Supported commands: weapon, rings, lowhealth, highhealth, max, stats
     * 
     * @param input The full cheat command string (e.g., "cheat weapon 1")
     */
    public static void handleCheat(String input) {
        if (game == null) {
            System.out.println("Error: Game not initialized.");
            return;
        }

        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println("Invalid cheat command. Usage: cheat <command>");
            return;
        }

        String command = parts[1].toLowerCase();

        switch (command) {
            case "weapon" -> handleWeaponCheat(parts);
            case "rings" -> handleRingsCheat(parts);
            case "lowhealth" -> handleLowHealthCheat();
            case "highhealth" -> handleHighHealthCheat();
            case "max" -> handleMaxCheat(parts);
            case "stats" -> handleStatsCheat();
            default -> System.out.println("Unknown cheat command: " + command);
        }
    }

    private static void handleWeaponCheat(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: cheat weapon <id> (0 to unequip)");
            return;
        }

        try {
            int weaponId = Integer.parseInt(parts[2]);
            if (weaponId == 0) {
                game.getPlayer().unequipWeapon();
                System.out.println("Weapon unequipped.");
            } else {
                Weapon weapon = EquipmentFactory.createWeapon(weaponId);
                if (weapon == null) {
                    System.out.println("Invalid weapon ID. Valid IDs: 1-6 (or 0 to unequip)");
                } else {
                    game.getPlayer().equipWeapon(weapon);
                    System.out.println("Equipped: " + weapon.getName());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid weapon ID. Must be a number.");
        }
    }

    private static void handleRingsCheat(String[] parts) {
        if (parts.length < 5) {
            System.out.println("Usage: cheat rings <id1> <id2> <id3> (0 for empty slot)");
            return;
        }

        try {
            int ring1Id = Integer.parseInt(parts[2]);
            int ring2Id = Integer.parseInt(parts[3]);
            int ring3Id = Integer.parseInt(parts[4]);

            // Equip rings in slots 0, 1, 2
            equipRingInSlot(ring1Id, 0);
            equipRingInSlot(ring2Id, 1);
            equipRingInSlot(ring3Id, 2);

            System.out.println("Rings equipped.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ring IDs. Must be numbers.");
        }
    }

    private static void equipRingInSlot(int ringId, int slot) {
        if (ringId == 0) {
            game.getPlayer().unequipRing(slot);
        } else {
            Ring ring = EquipmentFactory.createRing(ringId);
            if (ring == null) {
                System.out.println("Warning: Invalid ring ID " + ringId + " for slot " + (slot + 1));
            } else {
                game.getPlayer().equipRing(ring, slot);
            }
        }
    }

    /**
     * Sets all enemies to very low health (~1 hit to kill).
     * This setting persists only until the end of the match.
     */
    private static void handleLowHealthCheat() {
        game.getEnemyManager().setAllEnemyHealth(LOW_ENEMY_CHEAT_HEALTH);
        System.out.println("All enemies health set to "+ LOW_ENEMY_CHEAT_HEALTH + " (very low - ~1 hit to kill).");
    }

    /**
     * Sets all enemies to high health (hard to kill).
     * This setting persists only until the end of the match.
     */
    private static void handleHighHealthCheat() {
        game.getEnemyManager().setAllEnemyHealth(1000);
        System.out.println("All enemies health set to 1000 (high - hard to kill).");
    }

    private static void handleMaxCheat(String[] parts) {
        if (parts.length < 3) {
            System.out.println("Usage: cheat max <number>");
            return;
        }
        
        try {
            int max = Integer.parseInt(parts[2]);
            if (max < 1) {
                System.out.println("Error: max must be at least 1");
                return;
            }
            
            game.getGameBoard().setMaxValue(max);
            System.out.println("Max cell value set to " + max + " for this match.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Must be an integer.");
        }
    }

    private static void handleStatsCheat() {
        StatsUI.displayStats(game);
    }
}
