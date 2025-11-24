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

    public void resetFillStrength(){
        strength = 0;
    }
}
