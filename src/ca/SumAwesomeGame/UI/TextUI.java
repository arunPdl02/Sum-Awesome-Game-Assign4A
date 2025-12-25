package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.Event.GameEvent;
import ca.SumAwesomeGame.model.game.Event.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class TextUI implements GameObserver {
    public TextUI() {
        System.out.println("***************************************************");
        System.out.println("*** Welcome to Sum Awesome game by Arun & Surya ***");
        System.out.println("***************************************************");
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()){
            case INVALID_MOVE -> System.out.println("Invalid sum, no cells unlocked!");
            case FILL_COMPLETE -> System.out.println("Fill Complete! Strength is " + event.getValue());
            case PLAYER_ATTACKED -> System.out.println("Hit " + event.getCell() + " character for " + event.getValue() + " damage");
            case PLAYER_DIED -> System.out.println("Player died!");
            case ENEMY_DIED -> System.out.println("Enemy at "+ event.getCell() + "died!");
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }
}
