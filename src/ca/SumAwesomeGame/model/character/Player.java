package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.rings.RingsManager;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.Attack;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class Player implements GameObserver {
    private Game game;
    private WeaponsManager weapons;
    private RingsManager myRings;

    private Weapon equippedWeapon;
    private Ring[] equippedRings = new Ring[3];

    private int attackStrength;
    private int health = 1000;

    public Player() {
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void resetAttack(){
        attackStrength = 0;
    }

    public int getHealth() {
        return health;
    }

    private void attack(int fillStrength) {
//        Weapon currentWeapon = weapons.equipWeapon();
//        List<Ring> currentRings = myRings.getActiveRings();
        Attack attack = new Attack(fillStrength);
        attackStrength = attack.getAttackStrength();
    }

    public void reduceHealth(int EnemyAttackStrength) {
        health -= EnemyAttackStrength;
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()){
            case NEW_GAME -> {
                health = 1000;
                resetAttack();
            }
            case FILL_COMPLETE -> {
                attack(event.getValue());
                newEvent = new GameEvent(
                        GameEvents.PLAYER_ATTACKED,
                        event.getCellRow(),
                        event.getCellCol(),
                        event.getCell(),
                        attackStrength
                        );
            }
            case PLAYER_ATTACKED -> resetAttack();
            case ENEMY_ATTACKED -> {
                reduceHealth(event.getValue());
                if (health == 0){
                    newEvent = new GameEvent(GameEvents.PLAYER_DIED);
                }
            }
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
}
