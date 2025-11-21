package ca.SumAwesomeGame.model.character;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.rings.RingsManager;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.Attack;

import java.util.List;

public class Player {
    private WeaponsManager weapons;
    private RingsManager myRings;
    private final Attack attack = new Attack();

    public void Attack(){
        Weapon currentWeapon = weapons.equipWeapon();
        List<Ring> currentRings = myRings.getActiveRings();
        attack.initiateAttack(currentWeapon, currentRings);
    }
}
