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

    private Weapon equippedWeapon;
    private Ring[] equippedRings = new Ring[3];

    private final Attack attack = new Attack();
    private int health = 1000;
    private int fill = 0;

    public Player() {
    }

    public int getHealth() {
        return health;
    }

    public void attack() {
//        Weapon currentWeapon = weapons.equipWeapon();
//        List<Ring> currentRings = myRings.getActiveRings();
        attack.initiateAttack();
    }

    public int getFillStrength(){
        return fill;
    }

    public void increaseFillStrength(int strength){
        fill += strength;
    }

    public void resetFillStrength(){
        fill = 0;
    }
}
