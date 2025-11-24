package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.rings.RingsManager;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.Attack;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class Player implements GameObserver {
    private Game game;
    private WeaponsManager weapons;
    private RingsManager myRings;

    private Weapon equippedWeapon;
    private Ring[] equippedRings = new Ring[3];
    public boolean justAttacked = false;


    private int attackStrength;
    private int health = 1000;

    public Player() {
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void resetAttack(){
        attackStrength = 0;
        justAttacked = false;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    private void attack() {
//        Weapon currentWeapon = weapons.equipWeapon();
//        List<Ring> currentRings = myRings.getActiveRings();
        Attack attack = new Attack(game.getFill());
        attackStrength = attack.getAttackStrength();
        justAttacked = true;
    }

    public void reduceHealth(int EnemyAttackStrength) {
        health -= EnemyAttackStrength;
        // Health cannot be negative
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public void update() {
        if (game.isReadyToAttack()){
            attack();
            justAttacked = true;
        } else if (justAttacked) {
            resetAttack();
        }
        if (game.isStartNewGame()) {
            health = 1000;
            resetAttack();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
}
