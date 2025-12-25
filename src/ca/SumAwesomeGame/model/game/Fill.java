package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.game.Event.GameEvent;
import ca.SumAwesomeGame.model.game.Event.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.Arrays;

public class Fill implements GameObserver {
    private int strength = 0;
    private final boolean[][] fillCompleted = new boolean[3][3];
    private int numberOfCellUnlocked = 0;
    private int fillCount = 0;

    private void resetFill() {
        strength = 0;
        numberOfCellUnlocked = 0;
        for (boolean[] booleans : fillCompleted) {
            Arrays.fill(booleans, false);
        }
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()) {
            case VALID_MOVE ->{
                increaseFillStrength(event.getValue(),
                    event.getCellRow(),
                    event.getCellCol());
                if (isFillComplete()) {
                    newEvent = new GameEvent(
                            GameEvents.FILL_COMPLETE,
                            event.getCellRow(),
                            event.getCellCol(),
                            event.getCell(),
                            getFillStrength());
                }
            }
            case NEW_GAME, PLAYER_ATTACKED -> resetFill();
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }

    public int getFillStrength() {
        return strength;
    }

    public void increaseFillStrength(int increase, int row, int col) {
        if (!fillCompleted[row][col]) {
            numberOfCellUnlocked++;
            fillCompleted[row][col] = true;
        }
        fillCount++;
        strength += increase;
    }

    private boolean isFillComplete() {
        return numberOfCellUnlocked == 3;
    }

    public int getFillCount() {
        return fillCount;
    }
}
