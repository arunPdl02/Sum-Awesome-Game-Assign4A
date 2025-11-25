package ca.SumAwesomeGame.model.game;

import java.util.Optional;

public class Cell {
    private int value;
    private boolean cellLocked = true;
    private Optional<CellPosition> position = Optional.empty();
    private final int row;
    private final int col;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

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

    public String toString() {
        if (cellLocked) {
            return "  " + value + "  ";
        }
        return " _" + value + "_ ";
    }

}
