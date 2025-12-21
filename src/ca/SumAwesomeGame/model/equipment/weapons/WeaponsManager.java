package ca.SumAwesomeGame.model.equipment.weapons;

import java.util.Arrays;
import java.util.List;

public class WeaponsManager {
    private final List<Weapon> listOfWeapons = Arrays.asList(Weapon.values());
    private Weapon activeWeapon;

    public Weapon equipWeapon() {
        return activeWeapon;
    }
}
