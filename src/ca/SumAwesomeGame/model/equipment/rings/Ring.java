package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * Interface for rings that modify player attack damage.
 * Each ring can activate based on fill properties and provide damage bonuses.
 */
public interface Ring {
    /**
     * Gets the name of the ring
     * @return Ring name
     */
    String getName();

    /**
     * Gets the ability description of the ring
     * @return Ability description
     */
    String getAbility();

    /**
     * Checks if the ring should activate based on fill properties
     * @param fill The current fill
     * @return true if ring should activate
     */
    boolean shouldActivate(Fill fill);

    /**
     * Calculates the damage bonus multiplier if the ring activates
     * @param fill The current fill
     * @return Damage multiplier (e.g., 1.5 = 50% bonus, 2.0 = 100% bonus)
     *         Returns 1.0 if ring doesn't activate (no bonus)
     */
    double calculateDamageBonus(Fill fill);
}
