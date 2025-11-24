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


    /**
     * Replaces cells after a successful move:
     * - Center cell (1,1) gets the selected cell's value
     * - Selected cell gets a new random value
     */
    public void replaceCellsAfterMove(Cell selectedCell) {
        // Replace center cell with selected cell's value
        board[1][1].setValue(selectedCell.getValue());

        // Replace selected cell with random value
        int newRandomValue = GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE);

        int selectedRow = selectedCell.getRow();
        int selectedCol = selectedCell.getCol();

        board[selectedRow][selectedCol].setValue(newRandomValue);
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int randomValue = GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE);
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

    @Override
    public void update() {
        if (game.fillComplete() || game.isStartNewGame()) {
            initializeBoard();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
