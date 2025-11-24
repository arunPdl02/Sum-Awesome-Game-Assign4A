package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Diamond Sword weapon implementation.
 * Activates when cells are selected in descending order (non-increasing).
 * When activated, hits the primary target at 100% damage and adjacent enemies at 75% damage.
 * 
 * @author Sum Awesome Game Team
 */
public class DiamondSword implements Weapon {
    @Override
    public String getName() {
        return "Diamond Sword";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.isDescending();
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        List<AttackTarget> targets = new ArrayList<>();
        
        if (shouldActivate(fill)) {
            // Primary target is handled separately at 100%, we add side targets at 75%
            switch (primaryTarget) {
                case LEFT -> {
                    // Left side: also hit middle
                    if (team.getEnemyAt(Position.MIDDLE).isPresent()) {
                        targets.add(new AttackTarget(Position.MIDDLE, 0.75, false));
                    }
                }
                case MIDDLE -> {
                    // Middle: hit both sides
                    if (team.getEnemyAt(Position.LEFT).isPresent()) {
                        targets.add(new AttackTarget(Position.LEFT, 0.75, false));
                    }
                    if (team.getEnemyAt(Position.RIGHT).isPresent()) {
                        targets.add(new AttackTarget(Position.RIGHT, 0.75, false));
                    }
                }
                case RIGHT -> {
                    // Right side: also hit middle
                    if (team.getEnemyAt(Position.MIDDLE).isPresent()) {
                        targets.add(new AttackTarget(Position.MIDDLE, 0.75, false));
                    }
                }
            }
        }
        
        return targets;
    }
}

