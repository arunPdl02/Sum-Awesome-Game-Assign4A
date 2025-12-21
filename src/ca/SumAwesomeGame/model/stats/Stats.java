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
    private int damageDone;
    private int damageReceived;
    private int totalNumberOfFills;
    private List<Weapon> weaponsUsed = new ArrayList<>();

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getDamageDone() {
        return damageDone;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public int getTotalNumberOfFills() {
        return totalNumberOfFills;
    }

    public void addUsedWeapon(Weapon currentWeapon) {
        weaponsUsed.add(currentWeapon);
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()){
            case GAME_WON -> gamesWon++;
            case GAME_LOSS -> gamesLost--;
            case PLAYER_ATTACKED -> {
                damageDone += event.getValue();
                //TODO: add tracking for equipments
            }
            case ENEMY_ATTACKED -> damageReceived += event.getValue();
            case FILL_COMPLETE -> totalNumberOfFills += event.getValue();
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }
}
