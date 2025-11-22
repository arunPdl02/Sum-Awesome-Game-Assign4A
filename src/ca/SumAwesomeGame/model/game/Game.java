package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.Enemy;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.stats.Stats;
import ca.SumAwesomeGame.model.util.GameMath;

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
    public int playerInputCount;

    public Game() {
        gameId = 0;
        player = new Player();
    }

    private void createNewEnemies() {
        listOfEnemies.clear();
        for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
            listOfEnemies.add(new Enemy(i, player));
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
        playerInputCount = 0;
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
        playerInputCount++;
        if(allOuterCellsUnlocked()){
            player.attack();
            board = new GameBoard(ROW_SIZE,COL_SIZE);
            player.resetFillStrength();
        }
        if (playerInputCount % GameMath.getRandomValueBetween(3,5) == 0){
            Enemy randomEnemy = listOfEnemies.get(GameMath.getRandomValueBetween(0,3));
            randomEnemy.attack();
        }
    }

    private boolean allOuterCellsUnlocked() {
        int count = 0;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if(board.cellUnlocked(i,j)){
                    count++;
                }
            }
        }
//        return count == 8;
        return count == 3; //for testing have to implement duplication logic for fill
    }

    public Cell isSumValid(int sum) {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (!(i == 1 && j == 1)) {
                    if (board.checkSumOfTwoCells(1,1,i,j,sum)){
                        return board.getCell(i,j);
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
