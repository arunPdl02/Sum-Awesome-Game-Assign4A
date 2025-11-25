package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.UI.commands.Cheat;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameBoard;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.List;

public class MainUI implements Runnable, GameObserver {

    private TextUI text = new TextUI();
    private StatsUI statsUI = new StatsUI();
    private Game game;
    private GameBoard board;
    private int[] enemyHealth;

    public MainUI() {
        game = new Game();
        game.subscribe(this);
        statsUI.listenToGame(game);
    }

    @Override
    public void run() {
        String input = "";
        startGame();
        while (!input.equals("quit")) {
            printEnemies();
            printBoard();
            printPlayerStat();
            input = InputHandler.getInput();
            String[] inputArrayWord = input.split(" ", 2);

            try{
                switch (inputArrayWord[0]){
                    case "gear" -> showGear();
                    case "stats" -> showStats();
                    case "new" -> startGame();
                    case "cheat" -> Cheat.handleCheat(input);
                    case "quit" -> System.exit(0);
                    default -> game.play(Integer.parseInt(input));
                }
            } catch (NumberFormatException e){
                System.out.println("Enter a valid input.");
            } catch (IllegalArgumentException e){
                System.out.println("Invalid sum, no cells unlocked!");
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

    @Override
    public GameEvent update(GameEvent event) {
        switch (event.getEvent()){
            case INVALID_MOVE -> System.out.println("Invalid sum, no cells unlocked!");
        }
        return new GameEvent(GameEvents.NO_NEW_EVENT);
    }

    @Override
    public void listenToGame(Game game) {

    }
}
