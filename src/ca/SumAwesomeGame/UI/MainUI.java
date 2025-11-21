package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameBoard;

import java.util.List;

public class MainUI implements Runnable {

    private TextUI text;
    private Game game;
    private GameBoard board;
    private int[] enemyHealth;

    private final int ROW_SIZE = 3;
    private final int COL_SIZE = 3;

    public MainUI() {
        text = new TextUI();

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
        }
    }

    private void printPlayerStat() {
        System.out.printf("%15s", " [" + game.getPlayerHealth() + "] ");
        System.out.printf("%20s \n", "Fill Strength: " + game.getPlayerFill());
    }

    private void printEnemies() {
        List<Integer> enemyHealth = game.getEnemyHealth();
        for (Integer i: enemyHealth){
            System.out.printf("%-10s", " [" + i + "] ");
        }
        System.out.print("\n");
    }

    public void startGame() {
        board = new GameBoard(ROW_SIZE, COL_SIZE);
        game = new Game(board);
        game.startNewGame();
    }

    public void printBoard() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                System.out.printf("%-10s", board.getCell(i, j));
            }
            System.out.print("\n");
        }
    }
}
