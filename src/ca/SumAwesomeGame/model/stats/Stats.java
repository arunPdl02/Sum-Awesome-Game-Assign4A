package ca.SumAwesomeGame.model.stats;

import ca.SumAwesomeGame.model.equipment.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private int gamesWon;
    private int gamesLost;
    private int damageReceived;
    private int totalNumberOfFills;
    private List<Weapon> weaponsUsed = new ArrayList<>();

    public void addUsedWeapon(Weapon currentWeapon) {
        weaponsUsed.add(currentWeapon);
    }

}
