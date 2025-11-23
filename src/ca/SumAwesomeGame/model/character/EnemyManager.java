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
    private int updateCount;


    public EnemyManager(int numberOfEnemies){
        this.numberOfEnemies = numberOfEnemies;
    }

    @Override
    public void update() {
        updateCount++;
        if (updateCount % GameMath.getRandomValueBetween(3, 5) == 0) {
            Enemy oneRandomEnemy = listOfEnemies.get(GameMath.getRandomValueBetween(0, 2));
            oneRandomEnemy.attack();
        }
        if (game.didPlayerJustAttack()){
            enemyAttacked(game.getLastUnlockedCellPosition());
        } else if (game.startNewGame) {
            createNewSetOfEnemies();
        }
    }

    private void enemyAttacked(CellPosition lastUnlockedCellPosition) {
        System.out.println("here" + game.getPlayerAttackStrength() + lastUnlockedCellPosition);
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
            Enemy e = new Enemy(i, game.player);
            listOfEnemies.add(e);
        }
    }

    public List<Integer> getEnemyHealth() {
        return listOfEnemies.stream()
                .map(Enemy::getHealth)
                .toList();
    }

}
