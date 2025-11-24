package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.Position;
import ca.SumAwesomeGame.model.observer.GameObserver;
import ca.SumAwesomeGame.model.util.GameMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Triggers an enemy attack when player makes a failed move
     */
    public void attackPlayerOnFailedMove() {
        if (!listOfEnemies.isEmpty()) {
            // Select a random enemy to attack
            int randomIndex = GameMath.getRandomValueBetween(0, listOfEnemies.size() - 1);
            Enemy attackingEnemy = listOfEnemies.get(randomIndex);
            attackingEnemy.attack();
        }
    }

    // ========== OpponentTeam interface methods ==========
    
    /**
     * Gets the enemy at the specified position
     * @param position The position (LEFT, MIDDLE, RIGHT)
     * @return Optional containing the enemy, or empty if position is invalid or enemy is dead
     */
    public Optional<Enemy> getEnemyAt(Position position) {
        int index = positionToIndex(position);
        if (index < 0 || index >= listOfEnemies.size()) {
            return Optional.empty();
        }
        Enemy enemy = listOfEnemies.get(index);
        if (enemy.getHealth() <= 0) {
            return Optional.empty();
        }
        return Optional.of(enemy);
    }

    /**
     * Gets all enemies (including dead ones)
     * @return List of all enemies
     */
    public List<Enemy> getAllEnemies() {
        return new ArrayList<>(listOfEnemies);
    }

    /**
     * Gets a random alive enemy
     * @return Optional containing a random alive enemy, or empty if all are dead
     */
    public Optional<Enemy> getRandomEnemy() {
        List<Enemy> aliveEnemies = listOfEnemies.stream()
                .filter(e -> e.getHealth() > 0)
                .toList();
        if (aliveEnemies.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = GameMath.getRandomValueBetween(0, aliveEnemies.size() - 1);
        return Optional.of(aliveEnemies.get(randomIndex));
    }

    /**
     * Checks if all enemies are dead
     * @return true if all enemies have health <= 0
     */
    public boolean areAllDead() {
        return listOfEnemies.stream().allMatch(e -> e.getHealth() <= 0);
    }

    /**
     * Converts Position enum to list index
     * LEFT = 0, MIDDLE = 1, RIGHT = 2
     */
    private int positionToIndex(Position position) {
        return switch (position) {
            case LEFT -> 0;
            case MIDDLE -> 1;
            case RIGHT -> 2;
        };
    }

    /**
     * Converts CellPosition to Position for targeting
     * ONE (left 3 cells) -> LEFT
     * TWO (middle 2 cells) -> MIDDLE
     * THREE (right 3 cells) -> RIGHT
     */
    public Position cellPositionToPosition(CellPosition cellPosition) {
        return switch (cellPosition) {
            case ONE -> Position.LEFT;
            case TWO -> Position.MIDDLE;
            case THREE -> Position.RIGHT;
        };
    }

}
