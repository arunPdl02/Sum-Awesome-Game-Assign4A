package ca.SumAwesomeGame.model.game;

/**
 * Represents a target for an attack, including position and damage multiplier.
 */
public class AttackTarget {
    private final Position targetPosition;
    private final double damageMultiplier; // e.g., 1.0 = 100%, 0.5 = 50%
    private final boolean isPrimary;

    public AttackTarget(Position targetPosition, double damageMultiplier, boolean isPrimary) {
        this.targetPosition = targetPosition;
        this.damageMultiplier = damageMultiplier;
        this.isPrimary = isPrimary;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public boolean isPrimary() {
        return isPrimary;
    }
}

