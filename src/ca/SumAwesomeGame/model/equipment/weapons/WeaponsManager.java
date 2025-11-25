package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.game.Fill;

import java.util.ArrayList;
import java.util.List;

public class WeaponsManager {
    List<Weapon> weaponsList = new ArrayList<>();

    public WeaponsManager(){
        weaponsList.add(new DiamondSword());
        weaponsList.add(new FireStaff());
        weaponsList.add(new FrostBow());
        weaponsList.add(new LightningWand());
        weaponsList.add(new SparkleDagger());
        weaponsList.add(new StoneHammer());
    }

    public Weapon getActiveWeapon(Fill fill){
        return weaponsList.stream()
                .filter(weapon -> weapon.shouldActivate(fill))
                .findFirst()
                .orElse( new NullWeapon());
    }
}
