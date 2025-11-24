package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player attack with equipment bonuses and targeting.
 */
public class Attack {
    private final AttackResult result;

    public Attack(Fill fill, Player player, EnemyManager enemyManager) {
        // Get base damage from fill strength
        int baseDamage = fill.getFillStrength();
        
        // Calculate ring bonuses (multiply all active ring bonuses)
        double totalBonusMultiplier = 1.0;
        Map<String, Boolean> equipmentActivations = new HashMap<>();
        
        Weapon weapon = player.getEquippedWeapon();
        Ring[] rings = player.getEquippedRings();
        
        // Check weapon activation
        boolean weaponActivated = weapon.shouldActivate(fill);
        equipmentActivations.put(weapon.getName(), weaponActivated);
        
        // Check and apply ring bonuses
        for (Ring ring : rings) {
            if (ring != null) {
                boolean activated = ring.shouldActivate(fill);
                double ringBonus = ring.calculateDamageBonus(fill);
                equipmentActivations.put(ring.getName(), activated);
                if (activated) {
                    totalBonusMultiplier *= ringBonus;
                }
            }
        }
        
        // Get primary target from last unlocked cell position
        CellPosition lastCellPosition = fill.getLastUnlockedCellPosition();
        if (lastCellPosition == null) {
            // Fallback: use first position if no cell position available
            lastCellPosition = CellPosition.ONE;
        }
        Position primaryTarget = enemyManager.cellPositionToPosition(lastCellPosition);
        
        // Build list of attack targets
        List<AttackTarget> targets = new ArrayList<>();
        
        // Primary target always gets hit at 100% (base attack)
        targets.add(new AttackTarget(primaryTarget, 1.0, true));
        
        // Apply weapon targeting (adds additional targets)
        if (weaponActivated) {
            List<AttackTarget> weaponTargets = weapon.calculateAttackTargets(fill, primaryTarget, enemyManager);
            targets.addAll(weaponTargets);
        }
        
        this.result = new AttackResult(baseDamage, totalBonusMultiplier, targets, equipmentActivations);
    }

    /**
     * Legacy constructor for backward compatibility (not recommended)
     * @deprecated Use Attack(Fill, Player, EnemyManager) instead
     */
    @Deprecated
    public Attack(int fill) {
        // This is a legacy constructor - creates a minimal attack result
        this.result = new AttackResult(fill, 1.0, new ArrayList<>(), new HashMap<>());
    }

    public AttackResult getResult() {
        return result;
    }

    /**
     * Legacy method for backward compatibility
     * @deprecated Use getResult() instead
     */
    @Deprecated
    public int getAttackStrength() {
        return result.getBaseDamage();
    }
}
