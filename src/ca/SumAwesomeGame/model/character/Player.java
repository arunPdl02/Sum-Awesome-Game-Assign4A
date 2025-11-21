package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.rings.RingsManager;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.Attack;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private WeaponsManager weapons;
    private RingsManager myRings;

    private Weapon equippedWeapon;
    private Ring[] equippedRings = new Ring[3];

    private final Attack attack = new Attack();
    private int health;

    public Player() {
        health = 1000;
    }

    public void Attack() {
        Weapon currentWeapon = weapons.equipWeapon();
        List<Ring> currentRings = myRings.getActiveRings();
        attack.initiateAttack(currentWeapon, currentRings);
    }
}
