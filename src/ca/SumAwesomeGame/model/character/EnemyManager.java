package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.util.GameMath;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager implements GameObserver {
    private Game game;
    private final List<Enemy> listOfEnemies = new ArrayList<>();
    private final int numberOfEnemies;
    private int turnsSinceLastAttack = 0;
    private int attackInterval = GameMath.getRandomValueBetween(3, 5);


    public EnemyManager(int numberOfEnemies){
        this.numberOfEnemies = numberOfEnemies;
    }

    @Override
    public void update() {
        turnsSinceLastAttack++;
        if (turnsSinceLastAttack >= attackInterval) {
            // Attack with a random enemy
            if (!listOfEnemies.isEmpty()) {
                int randomIndex = GameMath.getRandomValueBetween(0, listOfEnemies.size() - 1);
                Enemy oneRandomEnemy = listOfEnemies.get(randomIndex);
                oneRandomEnemy.attack();
            }
            // Reset counter and pick new random interval for next attack
            turnsSinceLastAttack = 0;
            attackInterval = GameMath.getRandomValueBetween(3, 5);
        }
        if (game.didPlayerJustAttack()){
            enemyAttacked(game.getLastUnlockedCellPosition());
        } else if (game.isStartNewGame()) {
            createNewSetOfEnemies();
            // Reset attack timing for new match
            turnsSinceLastAttack = 0;
            attackInterval = GameMath.getRandomValueBetween(3, 5);
        }
    }

    private void enemyAttacked(CellPosition lastUnlockedCellPosition) {
        switch (lastUnlockedCellPosition){
            case ONE -> listOfEnemies.getFirst().reduceHealth(game.getPlayerAttackStrength());
            case TWO -> listOfEnemies.get(1).reduceHealth(game.getPlayerAttackStrength());
            case THREE -> listOfEnemies.get(2).reduceHealth(game.getPlayerAttackStrength());
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    private void createNewSetOfEnemies(){
        listOfEnemies.clear();
        for (int i = 0; i < numberOfEnemies; i++) {
            Enemy e = new Enemy(i, game.getPlayer());
            listOfEnemies.add(e);
        }
    }

    public List<Integer> getEnemyHealth() {
        return listOfEnemies.stream()
                .map(Enemy::getHealth)
                .toList();
    }

}
