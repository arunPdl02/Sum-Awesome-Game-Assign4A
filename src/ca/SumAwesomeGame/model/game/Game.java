package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    private long gameId;

    private GameBoard board;
    private EnemyManager enemies;

    private Player player = new Player();
    private final Fill fill = new Fill();
    private Stats stats = new Stats();

    private final int NUMBER_OF_ENEMIES = 3;
    public final int ROW_SIZE = 3;
    public final int COL_SIZE = 3;

    private boolean startNewGame = false;
    private int lastFillIncrease = 0;
    private CellPosition lastUnlockedCellPosition;
    private boolean readyToAttack = false;

    private static final List<GameObserver> observers = new ArrayList<>();


    public Game() {
        gameId = 0;
        board = new GameBoard(ROW_SIZE, COL_SIZE);
        enemies = new EnemyManager(NUMBER_OF_ENEMIES);

        fill.listenToGame(this);
        enemies.listenToGame(this);
        player.listenToGame(this);
        board.listenToGame(this);
        stats.listenToGame(this);
    }

    public List<Integer> getEnemyHealth() {
        return enemies.getEnemyHealth();
    }

    public void startNewGame() {
        gameId++;
        startNewGame = true;
        update();
        startNewGame = false;
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public boolean isPlayerDead() {
        return player.isDead();
    }

    public boolean isMatchLost() {
        return player.isDead();
    }

    public boolean isMatchWon() {
        return enemies.areAllDead();
    }

    public EnemyManager getEnemyManager() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public int getFill() {
        return fill.getFillStrength();
    }

    public Fill getFillObject() {
        return fill;
    }

    public boolean play(int sum) {
        Optional<Cell> validCellOptional = isSumValid(sum);
        
        // If sum is invalid, trigger enemy attack and return false
        if (validCellOptional.isEmpty()) {
            enemies.attackPlayerOnFailedMove();
            return false;
        }
        
        Cell validCell = validCellOptional.get();

        lastUnlockedCellPosition = validCell.unlockCell()
                .orElseThrow(UnsupportedOperationException::new);

        // Store the cell value for backward compatibility with observers
        lastFillIncrease = validCell.getValue();
        
        // Find the row and column of the selected cell
        int[] cellCoordinates = board.findCellCoordinates(validCell);
        int cellRow = cellCoordinates[0];
        int cellCol = cellCoordinates[1];
        
        // Add cell to fill (tracks position, value, order, time, count, and coordinates)
        if (lastUnlockedCellPosition != null) {
            fill.addCell(lastUnlockedCellPosition, validCell.getValue(), cellRow, cellCol);
        }
        
        // Replace cells after successful move: center = selected cell value, selected = random
        board.replaceCellsAfterMove(validCell);
        
        readyToAttack = fillComplete();

        update();

        if(didPlayerJustAttack()){
            readyToAttack = false;
            update();
        }
        
        return true; // Successful move
    }

    public boolean fillComplete() {
        // Use Fill's isComplete() method which tracks selected cells
        return fill.isComplete();
//        return fill.getSelectedCells().size() == 3; //for testing
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

    public CellPosition getLastUnlockedCellPosition() {
        return lastUnlockedCellPosition;
    }

    public boolean didPlayerJustAttack(){
        return player.justAttacked;
    }

    // Getters for observer access
    public boolean isStartNewGame() {
        return startNewGame;
    }

    public int getLastFillIncrease() {
        return lastFillIncrease;
    }

    public boolean isReadyToAttack() {
        return readyToAttack;
    }

    public GameBoard getGameBoard() {
        return board;
    }

}
