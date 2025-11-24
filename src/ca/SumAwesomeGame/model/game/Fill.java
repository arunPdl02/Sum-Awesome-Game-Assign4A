package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.observer.GameObserver;

public class Fill implements GameObserver {
    private Game game;
    private int strength = 0;

    @Override
    public void update() {
        if (game.isStartNewGame() || game.didPlayerJustAttack() ){
            resetFillStrength();
        } else {
            increaseFillStrength(game.getLastFillIncrease());
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    public int getFillStrength(){
        return strength;
    }

    public void increaseFillStrength(int increase){
        strength += increase;
    }

    public void resetFillStrength(){
        strength = 0;
    }
}
