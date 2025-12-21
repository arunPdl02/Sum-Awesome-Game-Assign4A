package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.util.GameMath;

public class GameBoard implements GameObserver {
    private final Cell[][] board;
    private Game game;

    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 15;

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

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int randomValue = GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE);
                CellPosition position = null;
                switch (j) {
                    case 0 -> position = CellPosition.FIRST;
                    case 1 -> position = CellPosition.SECOND;
                    case 2 -> position = CellPosition.THIRD;
                }
                board[i][j] = new Cell(randomValue, position, i, j);
            }
        }
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()) {
            case NEW_GAME, FILL_COMPLETE -> initializeBoard();
            case VALID_MOVE -> swapWithMiddle(
                    event.getCellRow(),
                    event.getCellCol(),
                    event.getValue()
            );
        }
        return newEvent;
    }

    private void swapWithMiddle(int row, int col, int value) {
        board[1][1].setValue(value);
        board[row][col].setValue(
                GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE)
        );
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
