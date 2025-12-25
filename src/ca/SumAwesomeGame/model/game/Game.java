package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.game.Event.GameEvent;
import ca.SumAwesomeGame.model.game.Event.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.*;

public class Game {
    public long gameId;

    private static final int NUMBER_OF_ENEMIES = 3;
    public static final int ROW_SIZE = 3;
    public static final int COL_SIZE = 3;

    private static final List<GameObserver> observers = new ArrayList<>();
    private final Queue<GameEvent> events = new LinkedList<>();

    private final GameBoard board = new GameBoard(ROW_SIZE, COL_SIZE);
    private final EnemyManager enemies = new EnemyManager(NUMBER_OF_ENEMIES);
    private final Fill fill = new Fill();
    public Player player = new Player(fill, enemies);

    public Game() {
        gameId = 0;
        fill.listenToGame(this);
        player.listenToGame(this);
        enemies.listenToGame(this);
        board.listenToGame(this);
    }

    public List<Integer> getEnemyHealth() {
        return enemies.getEnemyHealth();
    }

    public void startNewGame() {
        gameId++;
        events.add(new GameEvent(GameEvents.NEW_GAME));
        update();
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getFill() {
        return fill.getFillStrength();
    }

    public void play(int sum) {
        try {
            Cell validCell = isSumValid(sum)
                    .orElseThrow(IllegalArgumentException::new);
            CellPosition lastUnlockedCellPosition = validCell.unlockCell()
                    .orElseThrow(UnsupportedOperationException::new);
            events.add(new GameEvent(
                            GameEvents.VALID_MOVE,
                            validCell.getRow(),
                            validCell.getCol(),
                            lastUnlockedCellPosition,
                            validCell.getValue()
                    )
            );
        } catch (IllegalArgumentException e) {
            events.add(new GameEvent(GameEvents.INVALID_MOVE));
        }
        update();
    }

    public Optional<Cell> isSumValid(int sum) {
        Optional<Cell> lastMatch = Optional.empty();

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                if (!board.checkSumOfTwoCells(1, 1, i, j, sum)) {
                    continue;
                }
                Cell cell = board.getCell(i, j);
                lastMatch = Optional.of(cell);
                if (cell.isCellLocked()) {
                    return lastMatch;
                }
            }
        }
        return lastMatch;
    }

    public void update() {
        while (!events.isEmpty()) {
            GameEvent e = events.poll();
            for (GameObserver o : observers) {
                GameEvent newEvent = o.update(e);
                if (newEvent.getEvent() != GameEvents.NO_NEW_EVENT) {
                    events.add(newEvent);
                }
            }
        }
    }

    public void subscribe(GameObserver observer) {
        observers.add(observer);
    }

    public String getBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE; j++) {
                boardString.append(String.format(("%-10s"), board.getCell(i, j)));
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

}
