package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Stone Hammer weapon implementation.
 * Activates when fill is completed with a sufficient number of cell selections.
 * When activated, hits all enemy characters at 80% damage.
 * 
 * @author Sum Awesome Game Team
 */
public class StoneHammer implements Weapon {
    private static final int CELL_COUNT_THRESHOLD = 5; // TESTING: 5 cells (normally 10)

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
            // Hit all opponents at 80% (primary target already gets 100% from base attack)
            for (Position pos : Position.values()) {
                if (pos != primaryTarget && team.getEnemyAt(pos).isPresent()) {
                    targets.add(new AttackTarget(pos, 0.8, false));
                }
            }
        }
        
        return targets;
    }
}

