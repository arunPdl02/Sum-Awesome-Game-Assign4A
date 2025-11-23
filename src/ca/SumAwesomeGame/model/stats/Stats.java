package ca.SumAwesomeGame.model.stats;

import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class Stats implements GameObserver {
    private int gamesWon;
    private int gamesLost;
    private int damageReceived;
    private int totalNumberOfFills;
    private List<Weapon> weaponsUsed = new ArrayList<>();
    private Game game;

    public void addUsedWeapon(Weapon currentWeapon) {
        weaponsUsed.add(currentWeapon);
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
