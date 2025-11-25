package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.UI.commands.Cheat;
import ca.SumAwesomeGame.model.equipment.EquipmentFactory;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.AttackResult;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.Position;

import java.util.List;
import java.util.Map;

/**
 * Main user interface controller for the game.
 * Handles user input, displays game state, and manages the game loop.
 * Implements Runnable to run as a separate thread if needed.
 *
 * @author Sum Awesome Game Team
 */
public class MainUI implements Runnable {

    private StatsUI statsUI;
    private Game game;

    /**
     * Constructs a new MainUI and initializes the game.
     * Sets up observers and cheat command access.
     */
    public MainUI() {
        game = new Game();
        statsUI = new StatsUI(game.getStats());

        statsUI.listenToGame(game);
        Cheat.setGame(game); // Give Cheat access to game instance
    }

    @Override
    public void run() {
        String input = "";
        startGame();
        while (!input.equals("quit")) {
            // Don't show game state if player is dead or match is won
            if (!game.isPlayerDead() && !game.isMatchWon()) {
                printEnemies();
                printBoard();
                printPlayerStat();
            }
            input = InputHandler.getInput();
            String[] inputArrayWord = input.split(" ", 2);

            try {
                switch (inputArrayWord[0]) {
                    case "gear" -> showGear();
                    case "stats" -> showStats();
                    case "new" -> {
                        // If match is in progress (not won, not lost), count as forfeit/loss
                        if (!game.isMatchWon() && !game.isMatchLost()) {
                            game.getStats().recordForfeit();
                        }
                        startGame();
                        System.out.println("Started new game!");
                    }
                    case "cheat" -> Cheat.handleCheat(input);
                    case "quit" -> System.exit(0);
                    default -> {
                        // Check if player is already dead
                        if (game.isPlayerDead()) {
                            game.update(); // Trigger update so Stats can detect loss
                            handleMatchEnd(false); // Match lost
                            continue;
                        }

                        boolean success = game.play(Integer.parseInt(input));
                        if (!success) {
                            System.out.println("Invalid sum, no cells unlocked! Enemy attacks!");
                        }

                        // Display attack output if player just attacked
                        if (game.didPlayerJustAttack()) {
                            displayAttackOutput();
                        }

                        // Check if match ended (win or loss)
                        // Trigger update first so Stats can detect the transition
                        game.update();
                        if (game.isMatchWon()) {
                            handleMatchEnd(true); // Match won
                        } else if (game.isPlayerDead()) {
                            handleMatchEnd(false); // Match lost
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid input.");
            } catch (UnsupportedOperationException e) {
                System.out.println(e.toString());
            }

        }
    }

    /**
     * Displays the currently equipped weapon and rings.
     * Shows weapon name and ring names with their abilities.
     */
    private void showGear() {
        System.out.println("\n=== Current Equipment ===");

        // Show weapon
        Weapon weapon = game.getPlayer().getEquippedWeapon();
        System.out.println("Weapon: " + weapon.getName());
        if (!weapon.getName().equals("None")) {
            // Get ability description from weapon (if available)
            System.out.println("  (Weapon ability activates based on fill properties)");
        }

        // Show rings
        System.out.println("\nRings:");
        List<Ring> rings = game.getPlayer().getEquippedRings();
        if (rings.isEmpty()){
            System.out.println("  (No rings equipped)");
        } else {
            for(Ring ring: rings){
                System.out.println(ring.getName() + " with " + ring.getAbility());
            }
        }

        System.out.println("========================\n");
    }

    /**
     * Displays game statistics.
     */
    private void showStats() {
        statsUI.displayStats(game);
    }

    /**
     * Prints the player's current health and fill strength.
     */
    private void printPlayerStat() {
        System.out.printf("%15s", " [" + game.getPlayerHealth() + "] ");
        System.out.printf("%20s \n", "Fill Strength: " + game.getFill());
    }

    /**
     * Prints the health values of all enemies.
     */
    private void printEnemies() {
        List<Integer> enemyHealth = game.getEnemyHealth();
        for (Integer i : enemyHealth) {
            System.out.printf("%-10s", " [" + i + "] ");
        }
        System.out.print("\n");
    }

    /**
     * Starts a new game match.
     */
    public void startGame() {
        game.startNewGame();
    }

    /**
     * Prints the current game board state.
     */
    public void printBoard() {
        System.out.print(game.getBoard());
    }

    /**
     * Displays formatted attack output according to assignment requirements.
     * Shows fill strength, equipment activations, damage dealt, and enemy kills.
     */
    private void displayAttackOutput() {
        AttackResult result = game.getLastAttackResult();
        if (result == null) {
            return;
        }

        // Display fill complete message
        System.out.println("Fill complete! Strength is " + result.getBaseDamage() + ".");

        // Display ring activations
        Map<String, Boolean> activations = result.getEquipmentActivations();
        for (Map.Entry<String, Boolean> entry : activations.entrySet()) {
            String equipmentName = entry.getKey();
            boolean activated = entry.getValue();

            // Skip weapon for now (will handle separately)
            if (equipmentName.equals("None")) {
                continue;
            }

            // Check if it's a ring (not a weapon)
            List<Ring> rings = game.getPlayer().getEquippedRings();
            for (Ring ring : rings) {
                if (ring != null && ring.getName().equals(equipmentName)) {
                    if (activated) {
                        // Calculate bonus percentage from multiplier
                        double bonusMultiplier = getRingBonusMultiplier(ring);
                        int bonusPercent = (int) Math.round((bonusMultiplier - 1.0) * 100);
                        System.out.println(equipmentName + " adds " + bonusPercent + "% bonus damage.");
                    }
                    break;
                }
            }
        }

        // Get weapon name for targeting messages
        Weapon weapon = game.getPlayer().getEquippedWeapon();
        String weaponName = weapon.getName();

        // Display attack results for each target
        List<AttackTarget> targets = result.getTargets();
        for (AttackTarget target : targets) {
            Position pos = target.getTargetPosition();
            String positionName = pos.name().toLowerCase();

            int damage = result.calculateDamageForTarget(target);

            // Check if enemy is alive (getEnemyAt filters out dead enemies)
            var enemyOpt = game.getEnemyManager().getEnemyAt(pos);

            if (enemyOpt.isEmpty()) {
                // Enemy is dead - could be already dead or just killed
                // Check all enemies to see current health
                var allEnemies = game.getEnemyManager().getAllEnemies();
                var enemyAtPos = allEnemies.stream()
                        .filter(e -> e.getLocation() == pos.ordinal())
                        .findFirst();

                if (enemyAtPos.isPresent() && enemyAtPos.get().getHealth() == 0 && damage > 0) {
                    // Enemy health is 0 and damage was applied - it was just killed
                    if (target.isPrimary()) {
                        System.out.println("Hit " + positionName + " character for " + damage + " damage.");
                        System.out.println("Kills " + positionName + " character!");
                    } else {
                        System.out.println(weaponName + " targets " + positionName + " character.");
                        System.out.println("Hit " + positionName + " character for " + damage + " damage.");
                        System.out.println("Kills " + positionName + " character!");
                    }
                } else {
                    // Enemy was already dead - attack missed
                    if (target.isPrimary()) {
                        System.out.println("Missed " + positionName + " character.");
                    } else {
                        System.out.println(weaponName + " targets " + positionName + " character.");
                        System.out.println("Missed " + positionName + " character.");
                    }
                }
            } else {
                // Enemy is alive - show hit
                var enemy = enemyOpt.get();
                int currentHealth = enemy.getHealth();

                if (target.isPrimary()) {
                    System.out.println("Hit " + positionName + " character for " + damage + " damage.");
                } else {
                    System.out.println(weaponName + " targets " + positionName + " character.");
                    System.out.println("Hit " + positionName + " character for " + damage + " damage.");
                }

                // Check if enemy was killed (health is 0 after attack)
                if (currentHealth == 0) {
                    System.out.println("Kills " + positionName + " character!");
                }
            }
        }
    }

    /**
     * Helper method to get ring bonus multiplier for display
     */
    private double getRingBonusMultiplier(Ring ring) {
        // We need to recalculate the bonus, but we can use the fill strength
        // For now, we'll use a simple approach: check the ring's ability string
        String ability = ring.getAbility();

        // Parse percentage from ability string
        if (ability.contains("10%")) {
            return 1.1;
        } else if (ability.contains("50%")) {
            return 1.5;
        } else if (ability.contains("100%")) {
            return 2.0;
        } else if (ability.contains("1000%")) {
            return 11.0;
        }
        return 1.0;
    }

    /**
     * Handles match end (win or loss) and prompts user for next action.
     * If match is won, awards random equipment to the player.
     *
     * @param won true if match was won, false if match was lost
     */
    private void handleMatchEnd(boolean won) {
        if (won) {
            System.out.println("\n=== MATCH WON! ===");
            System.out.println("All enemies defeated!");

            // Give random equipment reward
            Object reward = EquipmentFactory.getRandomEquipment();
            if (reward instanceof Weapon) {
                Weapon weapon = (Weapon) reward;
                game.getPlayer().equipWeapon(weapon);
                System.out.println("Reward: " + weapon.getName() + " equipped!");
            } else if (reward instanceof Ring) {
                Ring ring = (Ring) reward;
                if (!game.getPlayer().canPlayerEquipMoreRings()) {
                    int ringIndexToReplace = InputHandler.getRingInput(game.getPlayer().getEquippedRings());
                    game.getPlayer().removeRingAtIndex(ringIndexToReplace);
                }
                game.getPlayer().addRing(ring);
                System.out.println("Reward: " + ring.getName() + " equipped!");
            }
        } else {
            System.out.println("\n=== MATCH LOST ===");
            System.out.println("Your character was defeated!");
        }

        System.out.println("\nWhat would you like to do?");
        System.out.println("  'new' - Start a new match");
        System.out.println("  'quit' - Exit the game");

        String choice = "";
        while (!choice.equals("new") && !choice.equals("quit")) {
            choice = InputHandler.getInput().toLowerCase().trim();
            if (choice.equals("new")) {
                startGame();
                System.out.println("Started new game!");
                return; // Return to main loop
            } else if (choice.equals("quit")) {
                System.exit(0);
            } else {
                System.out.println("Please enter 'new' or 'quit'");
            }
        }
    }
}
