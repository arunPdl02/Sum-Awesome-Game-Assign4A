package ca.SumAwesomeGame.model.game;

public class Damage {
    private final int damage;
    private final CellPosition enemyAttackedCellPosition;
    private final String attacker;

    public int getDamage() {
        return damage;
    }

    public CellPosition getEnemyCellPosition() {
        return enemyAttackedCellPosition;
    }

    public String getAttacker() {
        return attacker;
    }

    public Damage(int damage, CellPosition enemyAttackedCellPosition, String attacker) {
        this.damage = damage;
        this.enemyAttackedCellPosition = enemyAttackedCellPosition;
        this.attacker = attacker;
    }
}
