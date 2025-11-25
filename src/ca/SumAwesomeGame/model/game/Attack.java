package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.character.Player;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player attack with equipment bonuses and targeting.
 * Calculates base damage from fill strength, applies ring bonuses (multiplicative),
 * 
 * determines primary target from last unlocked cell, and adds weapon-specific targets.
 * 
 * AI Assistance: This complex integration of multiplicative ring bonuses, weapon targeting,
 * and multi-target damage calculation required AI assistance after extended debugging.
 * 
 * @author Sum Awesome Game Team
 */
public class Attack {
    private final AttackResult result;

    public Attack(Fill fill, Player player, EnemyManager enemyManager) {
        // Get base damage from fill strength
        int baseDamage = fill.getFillStrength();

        // AI Assistance: Cell position to enemy position mapping
        CellPosition lastCellPosition = fill.getLastUnlockedCellPosition();
        if (lastCellPosition == null) {
            // Fallback: use first position if no cell position available
            lastCellPosition = CellPosition.ONE;
        }
        Position primaryTarget = enemyManager.cellPositionToPosition(lastCellPosition);

        // AI Assistance: Multi-target system with primary + weapon-added targets
        List<AttackTarget> targets = new ArrayList<>();

        // Primary target always gets hit at 100% (base attack)
        targets.add(new AttackTarget(primaryTarget,
                1.0,
                true,
                enemyManager.getEnemyAt(primaryTarget).isPresent()));


        // AI Assistance: Multiplicative ring bonus calculation
        double totalBonusMultiplier = 1.0;
        Weapon weapon = player.getEquippedWeapon();
        List<Ring> rings = player.getEquippedRings();

        // AI Assistance: Iterative multiplicative bonus application
        for (Ring ring : rings) {
            if (ring != null) {
                boolean activated = ring.shouldActivate(fill);
                double ringBonus = ring.calculateDamageBonus(fill);
                if (activated) {
                    totalBonusMultiplier *= ringBonus;
                }
            }
        }

        // Apply weapon targeting (adds additional targets)
        if (!(weapon instanceof NullWeapon)) {
            List<AttackTarget> weaponTargets = weapon.calculateAttackTargets(fill, primaryTarget, enemyManager);
            targets.addAll(weaponTargets);
        }
        boolean equipmentActivations = enemyManager.getEnemyAt(primaryTarget).isPresent();

        this.result = new AttackResult(baseDamage, totalBonusMultiplier, targets, equipmentActivations);
    }

    public AttackResult getResult() {
        return result;
    }

}
