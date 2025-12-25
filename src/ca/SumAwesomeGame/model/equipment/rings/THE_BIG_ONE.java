package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

public class THE_BIG_ONE extends Ring{

    @Override
    public boolean canActivate(Fill fill) {
        return false;
    }

    @Override
    public double getBonus() {
        return 0.5;//50% damage
    }
}
