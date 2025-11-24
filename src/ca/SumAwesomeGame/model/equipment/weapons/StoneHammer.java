package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Stone Hammer: If fill completed with 10+ cells, hits all opponents at 80%
 */
public class StoneHammer implements Weapon {
    private static final int CELL_COUNT_THRESHOLD = 10;

    @Override
    public String getName() {
        return "Stone Hammer";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getCellCount() >= CELL_COUNT_THRESHOLD;
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        List<AttackTarget> targets = new ArrayList<>();
        
        if (shouldActivate(fill)) {
            // Hit all opponents at 80%
            // Primary target is already handled at 100%, so we add all three at 80%
            // But wait - if we add primary at 80%, it might override the 100%?
            // Actually, I think the intent is: primary gets 100% (normal), others get 80%
            // So we add the other two at 80%
            for (Position pos : Position.values()) {
                if (pos != primaryTarget && team.getEnemyAt(pos).isPresent()) {
                    targets.add(new AttackTarget(pos, 0.8, false));
                }
            }
        }
        
        return targets;
    }
}

