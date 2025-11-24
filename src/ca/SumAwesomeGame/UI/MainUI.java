package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.UI.commands.Cheat;
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
    }

    @Override
    public void run() {
        String input = "";
        startGame();
        while (!input.equals("quit")) {
            // Don't show game state if player is dead
            if (!game.isPlayerDead()) {
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
                            System.out.println("Game Over! You lost the match. Starting new match...");
                            startGame();
                            continue;
                        }
                        
                        boolean success = game.play(Integer.parseInt(input));
                        if (!success) {
                            System.out.println("Invalid sum, no cells unlocked! Enemy attacks!");
                        }
                        
                        // Check if player died after the move
                        if (game.isPlayerDead()) {
                            System.out.println("Game Over! You lost the match. Starting new match...");
                            startGame();
                        }
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid input.");
            } catch (UnsupportedOperationException e){
                System.out.println(e.toString());
            }

        }
    }

    private void showGear() {
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
}
