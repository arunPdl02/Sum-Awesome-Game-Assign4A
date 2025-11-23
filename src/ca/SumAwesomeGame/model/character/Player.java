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

    private final Attack attack = new Attack();
    private int health = 1000;

    public Player() {
    }

    public int getHealth() {
        return health;
    }

    private void attack() {
//        Weapon currentWeapon = weapons.equipWeapon();
//        List<Ring> currentRings = myRings.getActiveRings();
        attack.initiateAttack();
    }

    public void reduceHealth(int EnemyAttackStrength) {
        health -= EnemyAttackStrength;
    }

    @Override
    public void update() {
        if (game.allOuterCellsUnlocked()){
            attack();
        }
        if (game.startNewGame){
            health = 1000;
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
}
