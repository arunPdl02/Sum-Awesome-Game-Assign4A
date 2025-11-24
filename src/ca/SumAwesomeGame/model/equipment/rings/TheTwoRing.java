package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.util.GameMath;

/**
 * The Two Ring ring implementation.
 * Provides a 1000% damage bonus (11x multiplier) when fill strength is a power of 2.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class TheTwoRing implements Ring {
    private static final double BONUS_MULTIPLIER = 11.0; // 1000% bonus (11x = 10x + 1x base)

    @Override
    public String getName() {
        return "The Two Ring";
    }

    @Override
    public String getAbility() {
        return "1000% damage bonus if strength is a power of 2";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return GameMath.isPowerOfTwo(fill.getFillStrength());
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

