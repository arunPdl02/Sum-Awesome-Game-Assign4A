package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * Ring of Tenacity ring implementation.
 * Provides a 50% damage bonus when fill strength is divisible by 10.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class RingOfTenacity implements Ring {
    private static final double BONUS_MULTIPLIER = 1.5; // 50% bonus

    @Override
    public String getName() {
        return "Ring of Tenacity";
    }

    @Override
    public String getAbility() {
        return "50% damage bonus if strength % 10 == 0";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getFillStrength() % 2 == 0; // TESTING: % 2 (normally % 10)
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

