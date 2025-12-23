package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.*;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.util.GameMath;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager implements GameObserver {
    private final List<Enemy> listOfEnemies = new ArrayList<>();
    private final int numberOfEnemies;
    private int updateCount;

    public EnemyManager(int numberOfEnemies){
        this.numberOfEnemies = numberOfEnemies;
    }

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()){
            case VALID_MOVE -> {
                updateCount++;
                if (updateCount % GameMath.getRandomValueBetween(3, 5) == 0) {
                    newEvent = enemyAttacks();
                }
            }
            case NEW_GAME -> createNewSetOfEnemies();
            case PLAYER_ATTACKED -> {
                newEvent = enemyAttacks();
            }
            case INVALID_MOVE -> newEvent = enemyAttacks();
        }
        return newEvent;
    }

    private GameEvent enemyAttacks() {
        Enemy randomEnemy = getRandomEnemy();
        return new GameEvent(GameEvents.ENEMY_ATTACKED,
                -1,
                -1,
                randomEnemy.getLocation(),
                randomEnemy.getAttackStrength());
    }

    private Enemy getRandomEnemy() {
        //TODO only alive enemy attacks
        return listOfEnemies.get(GameMath.getRandomValueBetween(0,2));
    }

    public void playerAttacksEnemy(Damage damage) {
        int damageValue = damage.getDamage();
        switch (damage.getEnemyCellPosition()){
            case FIRST -> listOfEnemies.getFirst().reduceHealth(damageValue);
            case SECOND -> listOfEnemies.get(1).reduceHealth(damageValue);
            case THIRD -> listOfEnemies.get(2).reduceHealth(damageValue);
        }
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }

    private void createNewSetOfEnemies(){
        listOfEnemies.clear();
        for (int i = 0; i < numberOfEnemies; i++) {
            switch (i){
                case 0 -> listOfEnemies.add(new Enemy(CellPosition.FIRST));
                case 1 -> listOfEnemies.add(new Enemy(CellPosition.SECOND));
                case 2 -> listOfEnemies.add(new Enemy(CellPosition.THIRD));
            }
        }
    }

    public List<Integer> getEnemyHealth() {
        return listOfEnemies.stream()
                .map(Enemy::getHealth)
                .toList();
    }

}
