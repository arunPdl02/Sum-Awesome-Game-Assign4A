package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.game.GameEvent;
import ca.SumAwesomeGame.model.game.GameEvents;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class StatsUI implements GameObserver {
    private int gamesWon;
    private int gamesLost;
    private int damageDone;
    private int damageReceived;
    private int numberOfFillsCompleted;
    private List<Weapon> weaponsUsed = new ArrayList<>();

    private static final int LABEL_W = 10;
    private static final int VALUE_W = 20;
    private static final int INDENT = 5;
    private static final String ROW_FMT = " ".repeat(INDENT) + "%-" + LABEL_W + "s %" + VALUE_W + "d%n";

    @Override
    public GameEvent update(GameEvent event) {
        GameEvent newEvent = new GameEvent(GameEvents.NO_NEW_EVENT);
        switch (event.getEvent()){
            case GAME_WON -> gamesWon++;
            case GAME_LOSS -> gamesLost--;
            case PLAYER_ATTACKED -> {
                damageDone += event.getValue();
                //TODO: add tracking for equipments
            }
            case ENEMY_ATTACKED -> damageReceived += event.getValue();
            case FILL_COMPLETE -> numberOfFillsCompleted ++;
        }
        return newEvent;
    }

    @Override
    public void listenToGame(Game game) {
        game.subscribe(this);
    }

    public void showStats() {
        section("Equipment Activations");
        //TODO: printing equipments

        section("Matches:");
        row("Won", gamesWon);
        row("Loss", gamesLost);

        section("Total Damage:");
        row("Done", damageDone);
        row("Received", damageReceived);

        System.out.printf("Fills Completed: %19d\n", numberOfFillsCompleted);
    }

    private void section(String title){
        System.out.println(title);
    }

    private void row(String label, int value) {
        System.out.printf(ROW_FMT, label, value);
    }
}
