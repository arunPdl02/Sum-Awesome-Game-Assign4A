package ca.SumAwesomeGame.model.observer;


import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;

public interface GameObserver {

    GameEvent update(GameEvent event);

    void listenToGame(Game game);

}
