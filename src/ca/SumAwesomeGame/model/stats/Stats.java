package ca.SumAwesomeGame.model.stats;

import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Stats implements GameObserver {
    private int gamesWon;
    private int gamesLost;
    private int damageReceived;
    private int totalNumberOfFills;
    private List<Weapon> weaponsUsed = new ArrayList<>();

    public void addUsedWeapon(Weapon currentWeapon) {
        weaponsUsed.add(currentWeapon);
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);

        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }
}
