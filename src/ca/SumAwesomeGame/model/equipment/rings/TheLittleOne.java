package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * The Little One ring implementation.
 * Provides a 50% damage bonus when fill strength is at or below the threshold.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class TheLittleOne implements Ring {
    private static final int STRENGTH_THRESHOLD = 200; // TESTING: 200 (normally 90)
    private static final double BONUS_MULTIPLIER = 1.5; // 50% bonus

    @Override
    public String getName() {
        return "The Little One";
    }

    @Override
    public String getAbility() {
        return "50% Damage Bonus";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return fill.getFillStrength() <= STRENGTH_THRESHOLD;
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

