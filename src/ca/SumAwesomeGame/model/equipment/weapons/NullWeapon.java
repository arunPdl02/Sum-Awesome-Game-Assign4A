package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.Collections;
import java.util.List;

/**
 * Null Object pattern implementation for when player has no weapon equipped.
 */
public class NullWeapon implements Weapon {
    @Override
    public String getName() {
        return "None";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return false;
    }

    @Override
    public List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team) {
        return Collections.emptyList();
    }
}

