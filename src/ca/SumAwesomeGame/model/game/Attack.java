package ca.SumAwesomeGame.model.game;

import ca.SumAwesomeGame.model.character.EnemyManager;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;

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

    public void initiateAttack(Fill fill, Weapon weapon, CellPosition attackPosition){
        calculatePlayerAttackStrength(fill, attackPosition);

        calculateWeaponAttackStrength(fill, weapon, attackPosition);

        for (Damage damage : damages) {
            enemies.playerAttacksEnemy(damage);
        }
    }

    private void calculatePlayerAttackStrength(Fill fill, CellPosition attackPosition) {
        //TODO add ring bonus here and then decide the final attackStrength add event about ring bonuses
        int playerAttackStrength = fill.getFillStrength();
        damages.add(new Damage(playerAttackStrength, attackPosition, "Player"));
    }

    private void calculateWeaponAttackStrength(Fill fill, Weapon weapon, CellPosition lastUnlockedPosition) {
        if (weapon.canFire(fill)){
            List<Damage> weaponDamages = weapon.getDamages(lastUnlockedPosition);
            //TODO create event about weapon attacks
            damages.addAll(weaponDamages);
        }
    }

}
