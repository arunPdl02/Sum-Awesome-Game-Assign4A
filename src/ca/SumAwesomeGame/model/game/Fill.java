package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.*;

public class Fill implements GameObserver {
    private Game game;
    private int strength = 0;
    
    // Track selected cells
    private Set<CellPosition> selectedCells = new HashSet<>();
    
    // Track selection order (values in order they were selected)
    private List<Integer> selectionOrder = new ArrayList<>();
    
    // Track time when fill started
    private long fillStartTime = 0;
    
    // Track total cell count (including re-selections)
    private int cellCount = 0;

    @Override
    public void update() {
        if (game.isStartNewGame() || game.didPlayerJustAttack() ){
            resetFillStrength();
        } else {
            increaseFillStrength(game.getLastFillIncrease());
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    /**
     * Adds a cell to the fill and updates all tracking information
     */
    public void addCell(CellPosition position, int value) {
        // Start timer if this is the first cell
        if (selectedCells.isEmpty()) {
            fillStartTime = System.currentTimeMillis();
        }
        
        // Add to selected cells set
        selectedCells.add(position);
        
        // Add to selection order
        selectionOrder.add(value);
        
        // Increment cell count (includes re-selections)
        cellCount++;
        
        // Update strength
        strength += value;
    }

    public int getFillStrength(){
        return strength;
    }

    public void increaseFillStrength(int increase){
        strength += increase;
    }

    /**
     * Resets all fill state for a new fill
     */
    public void reset() {
        strength = 0;
        selectedCells.clear();
        selectionOrder.clear();
        fillStartTime = 0;
        cellCount = 0;
    }

    public void resetFillStrength(){
        reset();
    }

    /**
     * Returns the set of selected cell positions
     */
    public Set<CellPosition> getSelectedCells() {
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
     * Checks if the fill is complete (all 8 outer cells selected)
     */
    public boolean isComplete() {
        return selectedCells.size() == 8;
    }
}
