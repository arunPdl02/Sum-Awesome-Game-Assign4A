package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.Arrays;

public class Fill implements GameObserver {
    private int strength = 0;
    private final boolean[][] fillCompleted = new boolean[3][3];
    private int fillCount = 0;

    public Fill() {
        resetFill();
    }

    private void resetFill() {
        strength = 0;
        fillCount = 0;
        for (boolean[] booleans : fillCompleted) {
            Arrays.fill(booleans, false);
        }
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()) {
            case VALID_MOVE -> increaseFillStrength(event.getValue(),
                    event.getCellRow(),
                    event.getCellCol());
            case NEW_GAME, PLAYER_ATTACKED -> resetFill();
        }
        if (isFillComplete()) {
            newEvent = new GameEvent(GameEvents.FILL_COMPLETE);
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
            fillCount++;
            fillCompleted[row][col] = true;
        }
        strength += increase;
    }

    private boolean isFillComplete() {
        return fillCount == 8;
    }

}
