package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.game.Position;

import java.util.List;

/**
 * Interface for weapons that modify player attacks.
 * Each weapon can activate based on fill properties and modify targeting.
 * Follows the Open-Closed Principle: new weapons can be added without modifying existing code.
 * 
 * @author Sum Awesome Game Team
 */
public interface Weapon {
    /**
     * Gets the name of the weapon
     * @return Weapon name
     */
    String getName();

    /**
     * Checks if the weapon should activate based on fill properties
     * @param fill The current fill
     * @return true if weapon should activate
     */
    boolean shouldActivate(Fill fill);

    /**
     * Calculates additional attack targets based on weapon ability
     * @param fill The current fill
     * @param primaryTarget The primary target position (from last unlocked cell)
     * @param team The opponent team (for accessing enemies)
     * @return List of additional attack targets (empty if weapon doesn't activate)
     */
    List<AttackTarget> calculateAttackTargets(Fill fill, Position primaryTarget, EnemyManager team);
}
