package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class StatsUI implements GameObserver {
    private Game game;

    public StatsUI(){
    }

    @Override
    public void update() {

    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
