package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.UI.commands.Cheat;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameBoard;

import java.util.List;

public class MainUI implements Runnable{

    private TextUI text = new TextUI();
    private StatsUI statsUI = new StatsUI();
    private Game game;
    private GameBoard board;
    private int[] enemyHealth;

    public MainUI() {
        game = new Game();

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

            try{
                switch (inputArrayWord[0]){
                    case "gear" -> showGear();
                    case "stats" -> showStats();
                    case "new" -> startGame();
                    case "cheat" -> Cheat.handleCheat(input);
                    case "quit" -> System.exit(0);
                    default -> {
                        // Check if player is already dead
                        if (game.isPlayerDead()) {
                            handleMatchEnd(false); // Match lost
                            continue;
                        }
                        
                        game.play(Integer.parseInt(input));

                        // Check if match ended (win or loss)
                        if (game.isMatchWon()) {
                            handleMatchEnd(true); // Match won
                        } else if (game.isPlayerDead()) {
                            handleMatchEnd(false); // Match lost
                        }
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid input.");
            } catch (UnsupportedOperationException e){
                System.out.println(e.toString());
            } catch (IllegalArgumentException e){
                System.out.println("Invalid sum, no cells unlocked! Enemy attacks!");
            }

        }
    }

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
        Ring[] rings = game.getPlayer().getEquippedRings();
        boolean hasRings = false;
        for (int i = 0; i < rings.length; i++) {
            if (rings[i] != null) {
                hasRings = true;
                System.out.println("  Slot " + (i + 1) + ": " + rings[i].getName());
                System.out.println("    Ability: " + rings[i].getAbility());
            }
        }
        if (!hasRings) {
            System.out.println("  (No rings equipped)");
        }
        
        System.out.println("========================\n");
    }
    private void showStats(){
    }

    private void printPlayerStat() {
        System.out.printf("%15s", " [" + game.getPlayerHealth() + "] ");
        System.out.printf("%20s \n", "Fill Strength: " + game.getFill());
    }

    private void printEnemies() {
        List<Integer> enemyHealth = game.getEnemyHealth();
        for (Integer i: enemyHealth){
            System.out.printf("%-10s", " [" + i + "] ");
        }
        System.out.print("\n");
    }

    public void startGame() {
        game.startNewGame();
    }

    public void printBoard() {
        System.out.print(game.getBoard());
    }

    /**
     * Handles match end (win or loss) and prompts user for next action
     */
    private void handleMatchEnd(boolean won) {
        if (won) {
            System.out.println("\n=== MATCH WON! ===");
            System.out.println("All enemies defeated!");
            // TODO: Give random equipment reward
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
                return; // Return to main loop
            } else if (choice.equals("quit")) {
                System.exit(0);
            } else {
                System.out.println("Please enter 'new' or 'quit'");
            }
        }
    }
}
