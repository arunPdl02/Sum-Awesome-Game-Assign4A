package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Fire Staff: If fill completed with 15+ cells, hits primary target at 100% and sides at 50%
 */
public class FireStaff implements Weapon {
    private static final int CELL_COUNT_THRESHOLD = 5; // TESTING: 5 cells (normally 15)

    @Override
    public String getName() {
        return "Fire Staff";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getCellCount() >= CELL_COUNT_THRESHOLD;
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        List<AttackTarget> targets = new ArrayList<>();
        
        if (shouldActivate(fill)) {
            // Primary target is handled separately at 100%, we only add side targets at 50%
            switch (primaryTarget) {
                case LEFT -> {
                    // Left side: also hit middle
                    if (team.getEnemyAt(Position.MIDDLE).isPresent()) {
                        targets.add(new AttackTarget(Position.MIDDLE, 0.5, false));
                    }
                }
                case MIDDLE -> {
                    // Middle: hit both sides
                    if (team.getEnemyAt(Position.LEFT).isPresent()) {
                        targets.add(new AttackTarget(Position.LEFT, 0.5, false));
                    }
                    if (team.getEnemyAt(Position.RIGHT).isPresent()) {
                        targets.add(new AttackTarget(Position.RIGHT, 0.5, false));
                    }
                }
                case RIGHT -> {
                    // Right side: also hit middle
                    if (team.getEnemyAt(Position.MIDDLE).isPresent()) {
                        targets.add(new AttackTarget(Position.MIDDLE, 0.5, false));
                    }
                }
            }
        }
        
        return targets;
    }
}

