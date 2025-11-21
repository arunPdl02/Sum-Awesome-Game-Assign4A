package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.Enemy;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public long gameId;
    public GameBoard board;
    public Stats stats;
    private final int NUMBER_OF_ENEMIES = 3;
    public List<Enemy> listOfEnemies = new ArrayList<>();
    public Player player;

    public final int ROW_SIZE = 3;
    public final int COL_SIZE = 3;

    public Game() {
        gameId = 0;
        player = new Player();
    }

    private void createNewEnemies() {
        listOfEnemies.clear();
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            listOfEnemies.add(new Enemy(i));
        }
    }

    public List<Integer> getEnemyHealth() {
        List<Integer> enemyHealth = new ArrayList<>();
        for (Enemy enemy : listOfEnemies) {
            enemyHealth.add(enemy.getHealth());
        }
        return enemyHealth;
    }

    public void startNewGame() {
        gameId++;
        createNewEnemies();
        board = new GameBoard(ROW_SIZE, COL_SIZE);
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getPlayerFill() {
        return player.getFillStrength();
    }

    public void play(int sum) {
        Cell validCell = isSumValid(sum);
        validCell.unlockCell();
        player.increaseFillStrength(sum);
    }

    public Cell isSumValid(int sum) {
        int middleValue = board.getCell(1, 1).getValue();
        Cell currentCell;
        int summedValue;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (!(i == 1 && j == 1)) {
                    currentCell = board.getCell(i,j);
                    summedValue = currentCell.getValue() + middleValue;
                    System.out.println("summed :" + summedValue + "  input: " + sum);
                    if (summedValue == sum && currentCell.isCellLocked()) {
                        return board.getCell(i, j);
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public String getBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                boardString.append(String.format(("%-10s"), board.getCell(i, j)));
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

}
