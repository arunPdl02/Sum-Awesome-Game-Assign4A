package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * The Big One ring implementation.
 * Provides a 50% damage bonus when fill strength meets or exceeds the threshold.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class TheBigOne implements Ring {
    private static final int STRENGTH_THRESHOLD = 50; // TESTING: 50 (normally 160)
    private static final double BONUS_MULTIPLIER = 1.5; // 50% bonus

    @Override
    public String getName() {
        return "The Big One";
    }

    @Override
    public String getAbility() {
        return "50% damage bonus if strength >= 160";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getFillStrength() >= STRENGTH_THRESHOLD;
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

