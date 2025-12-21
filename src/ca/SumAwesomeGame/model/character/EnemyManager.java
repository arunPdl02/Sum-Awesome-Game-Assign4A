package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
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
                    newEvent = getEnemyAttackEvent();
                }
            }
            case NEW_GAME -> createNewSetOfEnemies();
            case PLAYER_ATTACKED -> {
                playerAttacksEnemy(event.getCell(), event.getValue());
                newEvent = getEnemyAttackEvent();
            }
            case INVALID_MOVE -> newEvent = getEnemyAttackEvent();
        }
        return newEvent;
    }

    private GameEvent getEnemyAttackEvent() {
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

    private void playerAttacksEnemy(CellPosition lastUnlockedCellPosition, int attackValue) {
        switch (lastUnlockedCellPosition){
            case FIRST -> listOfEnemies.getFirst().reduceHealth(attackValue);
            case SECOND -> listOfEnemies.get(1).reduceHealth(attackValue);
            case THIRD -> listOfEnemies.get(2).reduceHealth(attackValue);
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
