package ca.SumAwesomeGame.model.game.Attack;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.CellPosition;
import ca.SumAwesomeGame.model.game.Fill;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Attack by calculating attack strength using passed fill, weapon and rings
 */
public class Attack {
    private final List<Damage> damages = new ArrayList<>();
    private final EnemyManager enemies;

    public Attack(EnemyManager enemies) {
        this.enemies = enemies;
    }

    public void reset(){
        damages.clear();
    }

    public void initiateAttack(Fill fill, Weapon weapon, List<Ring> rings, CellPosition attackPosition){
        calculatePlayerAttackStrength(fill, rings, attackPosition);

        calculateWeaponAttackStrength(fill, weapon, attackPosition);

        for (Damage damage : damages) {
            enemies.playerAttacksEnemy(damage);
        }
    }

    private void calculatePlayerAttackStrength(Fill fill, List<Ring> rings, CellPosition attackPosition) {
        double damageMultiplier = 0;
        for(Ring r: rings){
            if (r.canActivate(fill)) {
                //TODO add event about ring bonuses
                damageMultiplier += r.getBonus();
            }
        }
        int fillStrength = fill.getFillStrength();

        int playerAttackStrength = (int) Math.ceil(
                fillStrength + damageMultiplier * fillStrength
        );
        damages.add(new Damage(playerAttackStrength, attackPosition, Attacker.PLAYER));
    }

    private void calculateWeaponAttackStrength(Fill fill, Weapon weapon, CellPosition lastUnlockedPosition) {
        if (weapon.canFire(fill)){
            List<Damage> weaponDamages = weapon.getDamages(lastUnlockedPosition);
            //TODO create event about weapon attacks
            damages.addAll(weaponDamages);
        }
    }

}
