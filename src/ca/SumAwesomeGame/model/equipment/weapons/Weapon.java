package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Damage;
import ca.SumAwesomeGame.model.game.Fill;

import java.util.List;

public abstract class Weapon {
    boolean hitFirstEnemy = false;
    boolean hitSecondEnemy = false;
    boolean hitThirdEnemy = false;

    public abstract boolean canFire(Fill fill);

    public abstract List<Damage> getDamages(CellPosition position);

    public abstract int strength(Fill fill);
}
