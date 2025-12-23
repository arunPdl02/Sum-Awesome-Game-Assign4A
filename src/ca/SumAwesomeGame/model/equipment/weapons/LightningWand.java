package ca.SumAwesomeGame.model.equipment.weapons;

import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Damage;
import ca.SumAwesomeGame.model.game.Fill;

import java.util.List;

public class LightningWand extends Weapon {

    @Override
    public boolean canFire(Fill fill) {
        return false;
    }

    @Override
    public List<Damage> getDamages(CellPosition position) {
        return null;
    }

    @Override
    public int strength(Fill fill) {
        return 0;
    }
}