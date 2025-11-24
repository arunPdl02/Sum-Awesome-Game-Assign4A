package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

/**
 * Ring of Tenacity: 50% damage bonus if strength % 10 == 0
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
        return fill.getFillStrength() % 10 == 0;
    }

    @Override
    public double calculateDamageBonus(Fill fill) {
        return shouldActivate(fill) ? BONUS_MULTIPLIER : 1.0;
    }
}

