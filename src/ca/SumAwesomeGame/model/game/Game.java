package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    public long gameId;

    public GameBoard board;
    private EnemyManager enemies;

    public Player player = new Player();
    private final Fill fill = new Fill();
    public Stats stats = new Stats();

    private final int NUMBER_OF_ENEMIES = 3;
    public final int ROW_SIZE = 3;
    public final int COL_SIZE = 3;


    private static final List<GameObserver> observers = new ArrayList<>();

    public Game() {
        gameId = 0;
        board = new GameBoard(ROW_SIZE, COL_SIZE);
        enemies = new EnemyManager(NUMBER_OF_ENEMIES);

        board.listenToGame(this);
        player.listenToGame(this);
        enemies.listenToGame(this);
        stats.listenToGame(this);
        fill.listenToGame(this);
    }


    public List<Integer> getEnemyHealth() {
        return enemies.getEnemyHealth();
    }

    public void startNewGame() {
        gameId++;
        enemies.createNewSetOfEnemies();
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getFill() {
        return fill.getFillStrength();
    }

    public void play(int sum) {
        Cell validCell = isSumValid(sum)
                .orElseThrow(IllegalArgumentException::new);
        validCell.unlockCell();
        fill.setLastFillIncrease(sum);
        update();
    }

    public boolean allOuterCellsUnlocked() {
        int count = 0;
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (board.cellUnlocked(i, j)) {
                    count++;
                }
            }
        }
        return count == 8;
//        return count == 3; //for testing have to implement duplication logic for fill
    }

    public Optional<Cell> isSumValid(int sum) {
        Optional<Cell> lastMatch = Optional.empty();

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {

                if (i == 1 && j == 1) {
                    continue;
                }

                if (!board.checkSumOfTwoCells(1, 1, i, j, sum)) {
                    continue;
                }

                Cell cell = board.getCell(i, j);
                lastMatch = Optional.of(cell);

                if (cell.isCellLocked()) {
                    return lastMatch;
                }
            }
        }
        return lastMatch;
    }


    public void update(){
        for (GameObserver e : observers) {
            e.update();
        }
    }

    public void subscribe(GameObserver observer){
        observers.add(observer);
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
