package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the result of a player attack, including targets, damage, and equipment activations.
 */
public class AttackResult {
    private final int baseDamage;
    private final double totalBonusMultiplier;
    private final List<AttackTarget> targets;
    private final Map<String, Boolean> equipmentActivations; // Equipment name -> activated

    public AttackResult(int baseDamage, double totalBonusMultiplier, List<AttackTarget> targets, 
                       Map<String, Boolean> equipmentActivations) {
        this.baseDamage = baseDamage;
        this.totalBonusMultiplier = totalBonusMultiplier;
        this.targets = new ArrayList<>(targets);
        this.equipmentActivations = new HashMap<>(equipmentActivations);
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

    public Map<String, Boolean> getEquipmentActivations() {
        return new HashMap<>(equipmentActivations);
    }

    /**
     * Calculates the final damage for a target based on its multiplier
     */
    public int calculateDamageForTarget(AttackTarget target) {
        double finalMultiplier = totalBonusMultiplier * target.getDamageMultiplier();
        return (int) Math.round(baseDamage * finalMultiplier);
    }
}

