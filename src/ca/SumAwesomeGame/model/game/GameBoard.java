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


    public boolean checkSumOfTwoCells(int row1, int col1, int row2, int col2, int sum){
        int val1 = board[row1][col1].getValue();
        int val2 = board[row2][col2].getValue();
        return sum == (val1 + val2);
    }

    public boolean cellUnlocked(int row, int col) {
        return !board[row][col].isCellLocked();
    }

    private void initializeBoard(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int randomValue = GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE);
                CellPosition position = null;
                switch (j){
                    case 0 -> position = CellPosition.ONE;
                    case 1 -> position = CellPosition.TWO;
                    case 2 -> position = CellPosition.THREE;
                }
                board[i][j] = new Cell(randomValue, position);
            }
        }
    }

    @Override
    public void update() {
        if (game.allOuterCellsUnlocked() || game.startNewGame){
            initializeBoard();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
