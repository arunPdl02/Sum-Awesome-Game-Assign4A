package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.stats.Stats;

public class Game {
    public long gameId;
    public GameBoard board;
    public Stats stats;

    public Game(GameBoard board) {
        this.board = board;
        gameId = 0;
    }

    public void startNewGame() {
        gameId++;
    }
}
