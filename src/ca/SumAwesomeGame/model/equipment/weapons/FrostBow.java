package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Frost Bow weapon implementation.
 * Activates when cells are selected in ascending order (non-decreasing).
 * When activated, hits all enemy characters at 100% damage.
 * 
 * @author Sum Awesome Game Team
 */
public class FrostBow implements Weapon {
    @Override
    public String getName() {
        return "Frost Bow";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.isAscending();
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        List<AttackTarget> targets = new ArrayList<>();
        
        if (shouldActivate(fill)) {
            // Hit all opponents at 100%
            // Primary target is already handled, so we add the other two
            for (Position pos : Position.values()) {
                if (pos != primaryTarget && team.getEnemyAt(pos).isPresent()) {
                    targets.add(new AttackTarget(pos, 1.0, false));
                }
            }
        }
        
        return targets;
    }
}

