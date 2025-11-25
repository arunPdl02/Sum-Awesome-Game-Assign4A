package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.*;

/**
 * Tracks the current fill state, including selected cells, strength, timing, and selection order.
 * A fill is completed when all 8 outer cells of the board have been selected.
 * Implements GameObserver to reset when a new game starts or after an attack.
 * 
 * @author Sum Awesome Game Team
 */
public class Fill implements GameObserver {
    private Game game;
    private int strength = 0;
    
    // Track selected cells by individual cell coordinates (row,col)
    private Set<String> selectedCells = new HashSet<>();
    
    // Track selection order (values in order they were selected)
    private List<Integer> selectionOrder = new ArrayList<>();
    
    // Track time when fill started
    private long fillStartTime = 0;
    
    // Track total cell count (including re-selections)
    private int cellCount = 0;
    
    // Track the most recently unlocked cell position
    private CellPosition lastUnlockedCellPosition;

    @Override
    public void update() {
        if (game.isStartNewGame() || game.didPlayerJustAttack() ){
            reset();
        }
        // Fill strength is now updated via addCell() method called directly from Game.play()
        // This update() method only handles reset scenarios
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    /**
     * Adds a cell to the fill and updates all tracking information
     * @param position The CellPosition (column) for enemy targeting
     * @param value The cell value
     * @param row The row coordinate of the cell
     * @param col The column coordinate of the cell
     */
    public void addCell(CellPosition position, int value, int row, int col) {
        // Start timer if this is the first cell
        if (selectedCells.isEmpty()) {
            fillStartTime = System.currentTimeMillis();
        }
        
        // Add to selected cells set by individual cell coordinates
        String cellKey = row + "," + col;
        selectedCells.add(cellKey);
        
        // Add to selection order
        selectionOrder.add(value);
        
        // Increment cell count (includes re-selections)
        cellCount++;
        
        // Update strength
        strength += value;
        
        // Track the last unlocked cell position (for enemy targeting)
        lastUnlockedCellPosition = position;
    }

    /**
     * Gets the current fill strength (sum of all selected cell values).
     * @return Current fill strength
     */
    public int getFillStrength(){
        return strength;
    }

    /**
     * Resets all fill state for a new fill.
     * Clears selected cells, selection order, timing, and resets strength to 0.
     */
    public void reset() {
        strength = 0;
        selectedCells.clear();
        selectionOrder.clear();
        fillStartTime = 0;
        cellCount = 0;
        lastUnlockedCellPosition = null;
    }

    /**
     * Returns the set of selected cell coordinates (as "row,col" strings)
     */
    public Set<String> getSelectedCells() {
        return new HashSet<>(selectedCells);
    }

    /**
     * Returns the selection order (list of cell values in order)
     */
    public List<Integer> getSelectionOrder() {
        return new ArrayList<>(selectionOrder);
    }

    /**
     * Returns the time elapsed since fill started in seconds
     */
    public long getTimeElapsed() {
        if (fillStartTime == 0) {
            return 0;
        }
        return (System.currentTimeMillis() - fillStartTime) / 1000;
    }

    /**
     * Returns the total number of cell additions (including re-selections)
     */
    public int getCellCount() {
        return cellCount;
    }

    /**
     * Returns the last unlocked cell position (most recently added to fill)
     */
    public CellPosition getLastUnlockedCellPosition() {
        return lastUnlockedCellPosition;
    }

    /**
     * Checks if the fill is complete (all 8 outer cells selected)
     */
    public boolean isComplete() {
        return selectedCells.size() == 8;
    }

    /**
     * Checks if selection order is ascending (non-decreasing, allows duplicates)
     */
    public boolean isAscending() {
        if (selectionOrder.size() < 2) {
            return false;
        }
        for (int i = 1; i < selectionOrder.size(); i++) {
            if (selectionOrder.get(i) < selectionOrder.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if selection order is descending (non-increasing, allows duplicates)
     */
    public boolean isDescending() {
        if (selectionOrder.size() < 2) {
            return false;
        }
        for (int i = 1; i < selectionOrder.size(); i++) {
            if (selectionOrder.get(i) > selectionOrder.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
