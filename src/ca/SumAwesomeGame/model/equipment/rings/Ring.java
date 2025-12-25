package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.game.Fill;

public abstract class Ring {

    public abstract boolean canActivate(Fill fill);

    public abstract double getBonus();

}
