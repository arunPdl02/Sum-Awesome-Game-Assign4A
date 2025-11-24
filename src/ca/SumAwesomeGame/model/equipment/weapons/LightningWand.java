package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightning Wand: If fill completed in <10s, targets additional random enemy at 100% damage
 */
public class LightningWand implements Weapon {
    private static final int TIME_THRESHOLD_SECONDS = 120; // TESTING: 120 seconds (normally 10)

    @Override
    public String getName() {
        return "Lightning Wand";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getTimeElapsed() < TIME_THRESHOLD_SECONDS;
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        List<AttackTarget> targets = new ArrayList<>();
        
        if (shouldActivate(fill)) {
            // Get a random alive enemy (might be same as primary target)
            team.getRandomEnemy().ifPresent(enemy -> {
                int location = enemy.getLocation();
                if (location >= 0 && location < 3) {
                    Position randomPosition = Position.values()[location];
                    targets.add(new AttackTarget(randomPosition, 1.0, false)); // 100% damage, not primary
                }
            });
        }
        
        return targets;
    }
}

