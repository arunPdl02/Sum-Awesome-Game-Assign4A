package ca.SumAwesomeGame.UI.commands;

import ca.SumAwesomeGame.UI.StatsUI;
import ca.SumAwesomeGame.model.equipment.EquipmentFactory;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;

public class Cheat {
    private static Game game;

    public static void setGame(Game gameInstance) {
        game = gameInstance;
    }

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

    private static void handleLowHealthCheat() {
        // TODO: Implement low health cheat
        System.out.println("Low health cheat not yet implemented.");
    }

    private static void handleHighHealthCheat() {
        // TODO: Implement high health cheat
        System.out.println("High health cheat not yet implemented.");
    }

    private static void handleMaxCheat(String[] parts) {
        // TODO: Implement max value cheat
        System.out.println("Max value cheat not yet implemented.");
    }

    private static void handleStatsCheat() {
        StatsUI.displayStats(game);
    }
}
