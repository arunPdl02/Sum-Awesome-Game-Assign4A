package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;

public class Fill implements GameObserver {
    private Game game;
    private int strength = 0;
    private int lastFillIncrease = 0;

    @Override
    public void update() {
        increaseFillStrength(lastFillIncrease);
        if (game.allOuterCellsUnlocked() || game.startNewGame){
            resetFillStrength();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    public void setLastFillIncrease(int lastFillIncrease) {
        this.lastFillIncrease = lastFillIncrease;
    }

    public int getFillStrength(){
        return strength;
    }

    public void increaseFillStrength(int increase){
        strength += increase;
    }

    private void resetFillStrength(){
        strength = 0;
    }
}
