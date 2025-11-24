package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;
import ca.SumAwesomeGame.model.util.GameMath;

/**
 * The Prime Directive ring implementation.
 * Provides a 100% damage bonus when fill strength is a prime number.
 * Ring bonuses are multiplicative with other active rings.
 * 
 * @author Sum Awesome Game Team
 */
public class ThePrimeDirective implements Ring {
    private static final double BONUS_MULTIPLIER = 2.0; // 100% bonus

    @Override
    public String getName() {
        return "The Prime Directive";
    }

    @Override
    public String getAbility() {
        return "100% damage bonus if strength is prime";
    }

    @Override
    public boolean shouldActivate(Fill fill) {
        return GameMath.isPrime(fill.getFillStrength());
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

