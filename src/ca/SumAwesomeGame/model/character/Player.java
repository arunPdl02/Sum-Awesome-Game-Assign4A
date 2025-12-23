package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.weapons.NoWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.*;
import ca.SumAwesomeGame.model.observer.GameObserver;


public class Player implements GameObserver {
    private final WeaponsManager weapons = new WeaponsManager();
    private final Fill fill;

    private Weapon currentWeapon = new NoWeapon();

    private int health = 1000;
    private final Attack attack;

    public Player(Fill fill, EnemyManager enemyManager) {
        this.fill = fill;
        this.attack = new Attack(enemyManager);
    }

    public void resetAttack(){
        attack.reset();
    }

    public int getHealth() {
        return health;
    }

    private void attack(CellPosition attackPosition) {
        //TODO rings logic
        attack.initiateAttack(fill, currentWeapon, attackPosition);
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
                attack(event.getCell());
                newEvent = new GameEvent(
                        GameEvents.PLAYER_ATTACKED,
                        event.getCellRow(),
                        event.getCellCol(),
                        event.getCell(),
                        event.getValue()
                        );
            }
            case PLAYER_ATTACKED -> resetAttack();
            case ENEMY_ATTACKED -> {
                reduceHealth(event.getValue());
                if (health == 0){
                    newEvent = new GameEvent(GameEvents.PLAYER_DIED);
                }
            }
            case GAME_WON -> currentWeapon = weapons.getRandomWeapon();
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }
}
