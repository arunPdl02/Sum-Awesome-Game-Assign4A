package ca.SumAwesomeGame.model.observer;


import ca.SumAwesomeGame.model.game.Game;

public interface GameObserver {

    void update();

    void listenToGame(Game game);

}
