package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.util.GameMath;
import java.util.ArrayList;
import java.util.List;

public class WeaponsManager {
    private final List<Weapon> listOfWeapons = new ArrayList<>();
    private final int NUMBER_OF_WEAPONS;

    public WeaponsManager() {
        listOfWeapons.add(new DiamondSword());
        listOfWeapons.add(new FireStaff());
        listOfWeapons.add(new FireStaff());
        listOfWeapons.add(new FrostBow());
        listOfWeapons.add(new LightningWand());
        listOfWeapons.add(new SparkleDagger());
        listOfWeapons.add(new StoneHammer());
        NUMBER_OF_WEAPONS = listOfWeapons.size();
    }

    public Weapon getRandomWeapon() {
        return listOfWeapons.get(
                GameMath.getRandomValueBetween(0, NUMBER_OF_WEAPONS)
        );
    }

    public Weapon getWeaponByName(WeaponEnum name){
        return listOfWeapons.stream()
                .filter(w -> w.getName() == name)
                .findFirst()
                .orElse(new NoWeapon());
    }
}
