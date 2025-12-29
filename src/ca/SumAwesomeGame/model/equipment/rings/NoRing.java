package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

public class NoRing extends Ring {
    @Override
    public RingsEnum getName() {
        return RingsEnum.NO_RING;
    }

    @Override
    public boolean canActivate(Fill fill) {
        return false;
    }

    @Override
    public double getBonus() {
        return 0;
    }
}
