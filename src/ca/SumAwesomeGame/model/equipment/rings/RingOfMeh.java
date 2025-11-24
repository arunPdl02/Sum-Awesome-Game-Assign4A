package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * Ring of Meh ring implementation.
 * Provides a 10% damage bonus when fill strength is divisible by 5.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class RingOfMeh implements Ring {
    private static final double BONUS_MULTIPLIER = 1.1; // 10% bonus

    @Override
    public String getName() {
        return "Ring of Meh";
    }

    @Override
    public String getAbility() {
        return "10% damage bonus if strength % 5 == 0";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getFillStrength() % 2 == 0; // TESTING: % 2 (normally % 5)
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

