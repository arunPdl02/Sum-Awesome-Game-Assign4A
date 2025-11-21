package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.Enemy;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.stats.Stats;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public long gameId;
    public GameBoard board;
    public Stats stats;
    private final int NUMBER_OF_ENEMIES = 3;
    public List<Enemy> listOfEnemies = new ArrayList<>();
    public Player player;

    public Game(GameBoard board) {
        this.board = board;
        gameId = 0;
        createNewEnemies();
        player = new Player();
    }

    private void createNewEnemies() {
        listOfEnemies.clear();
        for(int i = 0; i < NUMBER_OF_ENEMIES; i++){
            listOfEnemies.add(new Enemy(i));
        }
    }

    public List<Integer> getEnemyHealth(){
        List<Integer> enemyHealth = new ArrayList<>();
        for(Enemy enemy: listOfEnemies){
            enemyHealth.add(enemy.getHealth());
        }
        return enemyHealth;
    }

    public void startNewGame() {
        gameId++;
        createNewEnemies();
    }

    public int getPlayerHealth() {
        return player.getHealth();
    }

    public int getPlayerFill() {
        return player.getFillStrength();
    }
}
