package ca.SumAwesomeGame.model.observer;


import ca.SumAwesomeGame.model.game.Game;

/**
 * Observer interface for the Observer pattern implementation.
 * Classes implementing this interface can subscribe to game events and receive
 * notifications when game state changes occur.
 * 
 * @author Sum Awesome Game Team
 */
public interface GameObserver {

    /**
     * Called when a game state change occurs.
     * Observers should check game state and react accordingly.
     */
    void update();

    /**
     * Subscribes this observer to game events.
     * @param game The game instance to observe
     */
    void listenToGame(Game game);

}
