package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.util.GameMath;

/**
 * Manages the 3x3 game board grid of cells.
 * Handles cell initialization, replacement after moves, and max value configuration.
 * Implements GameObserver to reset the board when a new game starts or fill completes.
 * 
 * @author Sum Awesome Game Team
 */
public class GameBoard implements GameObserver {
    private final Cell[][] board;
    private Game game;

    private final int MIN_VALUE = 0;
    private int maxValue = 15; // Default max value, can be changed via cheat

    public GameBoard(int row, int col) {
        board = new Cell[row][col];
        initializeBoard();
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }


    public boolean checkSumOfTwoCells(int row1, int col1, int row2, int col2, int sum) {
        int val1 = board[row1][col1].getValue();
        int val2 = board[row2][col2].getValue();
        return sum == (val1 + val2);
    }

    public boolean cellUnlocked(int row, int col) {
        return !board[row][col].isCellLocked();
    }

    /**
     * Replaces cells after a successful move:
     * - Center cell (1,1) gets the selected cell's value
     * - Selected cell gets a new random value within current max value range
     */
    public void replaceCellsAfterMove(Cell selectedCell) {
        int[] coordinates = findCellCoordinates(selectedCell);
        int selectedRow = coordinates[0];
        int selectedCol = coordinates[1];
        
        if (selectedRow == -1 || selectedCol == -1) {
            return; // Cell not found, cannot proceed
        }
        
        int selectedCellValue = selectedCell.getValue();
        board[1][1].setValue(selectedCellValue);
        
        // Generate new random value respecting current max value (may be modified by cheat)
        int newRandomValue = GameMath.getRandomValueBetween(MIN_VALUE, maxValue);
        board[selectedRow][selectedCol].setValue(newRandomValue);
    }
    
    /**
     * Finds the row and column coordinates of a cell in the board
     * @param cell The cell to find
     * @return int array [row, col], or [-1, -1] if not found
     */
    public int[] findCellCoordinates(Cell cell) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == cell) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int randomValue = GameMath.getRandomValueBetween(MIN_VALUE, maxValue);
                CellPosition position = null;
                switch (j) {
                    case 0 -> position = CellPosition.ONE;
                    case 1 -> position = CellPosition.TWO;
                    case 2 -> position = CellPosition.THREE;
                }
                board[i][j] = new Cell(randomValue, position, i, j);
            }
        }
    }
    
    /**
     * Sets the maximum value for random cell generation
     * @param max The maximum value (must be at least 1)
     */
    public void setMaxValue(int max) {
        if (max < 1) {
            // Silently ignore invalid values (model shouldn't print)
            return;
        }
        this.maxValue = max;
    }
    
    /**
     * Resets the maximum value to the default (15)
     */
    public void resetMaxValue() {
        this.maxValue = 15;
    }
    
    /**
     * Gets the current maximum value
     * @return The current maximum value
     */
    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public void update() {
        if (game.fillComplete() || game.isStartNewGame()){
            if (game.isStartNewGame()) {
                resetMaxValue(); // Reset to default when new match starts
            }
            initializeBoard();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
