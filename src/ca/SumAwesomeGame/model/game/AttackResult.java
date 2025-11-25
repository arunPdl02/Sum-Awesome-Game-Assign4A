package ca.SumAwesomeGame.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the result of a player attack, including targets, damage, and equipment activations.
 * Encapsulates all information needed to display and apply the attack.
 * 
 * @author Sum Awesome Game Team
 */
public class AttackResult {
    private final int baseDamage;
    private final double totalBonusMultiplier;
    private final List<AttackTarget> targets;
    private boolean weaponHitEnemy; // Equipment name -> activated

    public AttackResult(int baseDamage, double totalBonusMultiplier, List<AttackTarget> targets, 
                       boolean weaponHitEnemy) {
        this.baseDamage = baseDamage;
        this.totalBonusMultiplier = totalBonusMultiplier;
        this.targets = new ArrayList<>(targets);
        this.weaponHitEnemy = weaponHitEnemy;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public double getTotalBonusMultiplier() {
        return totalBonusMultiplier;
    }

    public List<AttackTarget> getTargets() {
        return new ArrayList<>(targets);
    }

    public boolean getWeaponHitEnemy() {
        return weaponHitEnemy;
    }

    /**
     * Calculates the final damage for a target based on its multiplier.
     * AI Assistance: Complex damage calculation combining base damage, multiplicative ring bonuses,
     * and per-target damage multipliers.
     */
    public int calculateDamageForTarget(AttackTarget target) {
        double finalMultiplier = totalBonusMultiplier * target.getDamageMultiplier();
        return (int) Math.round(baseDamage * finalMultiplier);
    }
}

