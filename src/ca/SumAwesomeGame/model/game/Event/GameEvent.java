package ca.SumAwesomeGame.model.game.Event;

import ca.SumAwesomeGame.model.game.CellPosition;

public class GameEvent {
    private final GameEvents event;
    private final int cellRow;
    private final int cellCol;
    private final CellPosition cell;
    private final int value;

    public GameEvent(GameEvents event, int cellRow, int cellCol, CellPosition cell, int value) {
        this.event = event;
        this.cellRow = cellRow;
        this.cellCol = cellCol;
        this.cell = cell;
        this.value = value;
    }

    public GameEvent(GameEvents event) {
        this.event = event;
        this.cellRow = -1;
        this.cellCol = -1;
        this.cell = CellPosition.NONE;
        this.value = -1;
    }

    public GameEvents getEvent() {
        return event;
    }

    public int getCellRow() {
        return cellRow;
    }

    public int getCellCol() {
        return cellCol;
    }

    public CellPosition getCell() {
        return cell;
    }

    public int getValue() {
        return value;
    }
}
