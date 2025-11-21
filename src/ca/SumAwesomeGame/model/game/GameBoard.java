package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.util.GameMath;

public class GameBoard {
    private final Cell[][] board;

    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 15;

    public GameBoard(int row, int col) {
        board = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Cell(GameMath.getRandomValueBetween(MIN_VALUE, MAX_VALUE));
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }


}
