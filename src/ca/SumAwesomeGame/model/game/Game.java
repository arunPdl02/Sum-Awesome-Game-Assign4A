package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Main game controller class that manages the game state, board, players, enemies, and game flow.
 * Implements the Observer pattern to notify observers of game state changes.
 * 
 * @author Sum Awesome Game Team
 */
public class Game {
    private GameBoard board;
    private EnemyManager enemies;

    private Player player = new Player();
    private final Fill fill = new Fill();
    private Stats stats = new Stats();

    private final int NUMBER_OF_ENEMIES = 3;
    public final int ROW_SIZE = 3;
    public final int COL_SIZE = 3;

    private boolean startNewGame = false;
    private CellPosition lastUnlockedCellPosition;
    private boolean readyToAttack = false;

    private static final List<GameObserver> observers = new ArrayList<>();


    /**
     * Constructs a new Game instance and initializes all game components.
     * Sets up the game board, enemies, player, fill tracker, and stats.
     * Subscribes all observers to game events.
     */
    public Game() {
        board = new GameBoard(ROW_SIZE, COL_SIZE);
        enemies = new EnemyManager(NUMBER_OF_ENEMIES);

        fill.listenToGame(this);
        enemies.listenToGame(this);
        player.listenToGame(this);
        board.listenToGame(this);
        stats.listenToGame(this);
    }

    /**
     * Gets the health values of all enemies.
     * @return List of enemy health values
     */
    public List<Integer> getEnemyHealth() {
        return enemies.getEnemyHealth();
    }

    /**
     * Starts a new match by resetting game state and notifying observers.
     */
    public void startNewGame() {
        startNewGame = true;
        update();
        startNewGame = false;
    }

    /**
     * Gets the current health of the player.
     * @return Player's current health
     */
    public int getPlayerHealth() {
        return player.getHealth();
    }

    /**
     * Checks if the player is dead.
     * @return true if player health is 0 or less, false otherwise
     */
    public boolean isPlayerDead() {
        return player.isDead();
    }

    /**
     * Checks if the match has been lost (player is dead).
     * @return true if match is lost, false otherwise
     */
    public boolean isMatchLost() {
        return player.isDead();
    }

    /**
     * Checks if the match has been won (all enemies are dead).
     * @return true if match is won, false otherwise
     */
    public boolean isMatchWon() {
        return enemies.areAllDead();
    }

    /**
     * Gets the enemy manager instance.
     * @return EnemyManager instance
     */
    public EnemyManager getEnemyManager() {
        return enemies;
    }

    /**
     * Gets the player instance.
     * @return Player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the current fill strength.
     * @return Current fill strength value
     */
    public int getFill() {
        return fill.getFillStrength();
    }

    /**
     * Gets the Fill object for direct access.
     * @return Fill instance
     */
    public Fill getFillObject() {
        return fill;
    }

    /**
     * Processes a player move by attempting to unlock a cell with the given sum.
     * If the sum is valid, unlocks the cell, updates the board, and checks for fill completion.
     * If the sum is invalid, triggers an enemy attack.
     * 
     * @param sum The sum value the player is trying to match
     * @return true if the move was successful, false if the sum was invalid
     */
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
        int lastFillIncrease = validCell.getValue();
        
        // Add cell to fill (tracks position, value, order, time, count, and coordinates)
        if (lastUnlockedCellPosition != null) {
            fill.addCell(lastUnlockedCellPosition,
                    validCell.getValue(),
                    validCell.getRow(),
                    validCell.getCol());
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

    /**
     * Checks if the fill is complete (8 cells selected).
     * @return true if fill is complete, false otherwise
     */
    public boolean fillComplete() {
        // Use Fill's isComplete() method which tracks selected cells
        return fill.isComplete();
//        return fill.getSelectedCells().size() == 3; //for testing
    }

    /**
     * Validates if a given sum can be formed by adding the center cell with any locked cell.
     * @param sum The sum to validate
     * @return Optional containing a valid Cell if found, empty otherwise
     */
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

    /**
     * Notifies all subscribed observers of game state changes.
     */
    public void update(){
        for (GameObserver e : observers) {
            e.update();
        }
    }

    /**
     * Subscribes an observer to game state changes.
     * @param observer The observer to subscribe
     */
    public void subscribe(GameObserver observer){
        observers.add(observer);
    }

    /**
     * Gets a string representation of the game board.
     * @return Formatted string of the board state
     */
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

    /**
     * Gets the position of the last unlocked cell.
     * @return CellPosition of the last unlocked cell
     */
    public CellPosition getLastUnlockedCellPosition() {
        return lastUnlockedCellPosition;
    }

    /**
     * Checks if the player just performed an attack.
     * @return true if player just attacked, false otherwise
     */
    public boolean didPlayerJustAttack(){
        return player.justAttacked;
    }

    /**
     * Checks if a new game is starting.
     * @return true if new game is starting, false otherwise
     */
    public boolean isStartNewGame() {
        return startNewGame;
    }

    /**
     * Checks if the player is ready to attack (fill is complete).
     * @return true if ready to attack, false otherwise
     */
    public boolean isReadyToAttack() {
        return readyToAttack;
    }

    /**
     * Gets the game board instance.
     * @return GameBoard instance
     */
    public GameBoard getGameBoard() {
        return board;
    }

    /**
     * Gets the result of the last attack performed by the player.
     * @return AttackResult of the last attack, or null if no attack has occurred
     */
    public AttackResult getLastAttackResult() {
        return player.getLastAttackResult();
    }

    /**
     * Gets the stats tracker instance.
     * @return Stats instance
     */
    public Stats getStats() {
        return stats;
    }

}
