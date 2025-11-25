package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class StatsUI implements GameObserver {
    private Game game;

    public StatsUI(){
    }

    @Override
    public GameEvent update(GameEvent event) {

        return new GameEvent(GameEvents.NO_NEW_EVENT);
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);

    }
}
