package ca.SumAwesomeGame.model.game.Attack;

import ca.SumAwesomeGame.model.game.CellPosition;

public class Damage {
    private final int damage;
    private final CellPosition enemyAttackedCellPosition;
    private final Attacker attacker;

    public int getDamage() {
        return damage;
    }

    public CellPosition getEnemyCellPosition() {
        return enemyAttackedCellPosition;
    }

    public Attacker getAttacker() {
        return attacker;
    }

    public Damage(int damage, CellPosition enemyAttackedCellPosition, Attacker attacker) {
        this.damage = damage;
        this.enemyAttackedCellPosition = enemyAttackedCellPosition;
        this.attacker = attacker;
    }
}
