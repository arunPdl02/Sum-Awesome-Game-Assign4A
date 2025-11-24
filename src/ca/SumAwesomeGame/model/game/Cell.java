package ca.SumAwesomeGame.model.game;

import java.util.Optional;

/**
 * Represents a single cell in the game board grid.
 * Each cell contains a numeric value, position information, and lock state.
 * Cells can be locked (not yet selected) or unlocked (part of current fill).
 * 
 * @author Sum Awesome Game Team
 */
public class Cell {
    private int value;
    private boolean cellLocked = true;
    private Optional<CellPosition> position = Optional.empty();
    private final int row;
    private final int col;

    public int getValue() {
        return value;
    }

    public Cell(int value, CellPosition position, int row, int col) {
        this.value = value;
        this.position = Optional.ofNullable(position);
        this.row = row;
        this.col = col;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCellLocked() {
        return cellLocked;
    }

    public Optional<CellPosition> unlockCell() {
        cellLocked = false;
        return position;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public String toString() {
        if (cellLocked) {
            return "  " + value + "  ";
        }
        return " _" + value + "_ ";
    }

}
