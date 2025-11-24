package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.game.Attack;
import ca.SumAwesomeGame.model.game.AttackResult;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

public class Player implements GameObserver {
    private Game game;
    private Weapon equippedWeapon = new NullWeapon();
    private Ring[] equippedRings = new Ring[3]; // null = empty slot
    public boolean justAttacked = false;
    private AttackResult lastAttackResult = null; // Store last attack result for UI display

    private int health = 3000; // Increased for easier testing

    public Player() {
    }

    public void resetAttack(){
        justAttacked = false;
        lastAttackResult = null; // Clear attack result after display
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    private void attack() {
        // Create attack with equipment integration
        Attack attack = new Attack(game.getFillObject(), this, game.getEnemyManager());
        AttackResult result = attack.getResult();
        
        // Store result for UI display
        lastAttackResult = result;
        
        // Apply damage to enemies based on attack targets
        for (AttackTarget target : result.getTargets()) {
            game.getEnemyManager().getEnemyAt(target.getTargetPosition()).ifPresent(enemy -> {
                int damage = result.calculateDamageForTarget(target);
                enemy.reduceHealth(damage);
            });
        }
        
        justAttacked = true;
    }
    
    public AttackResult getLastAttackResult() {
        return lastAttackResult;
    }

    // ========== Equipment Management Methods ==========
    
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    public void unequipWeapon() {
        equippedWeapon = new NullWeapon();
    }

    public void equipRing(Ring ring, int slot) {
        if (slot < 0 || slot >= 3) {
            throw new IllegalArgumentException("Ring slot must be 0, 1, or 2");
        }
        equippedRings[slot] = ring;
    }

    public void unequipRing(int slot) {
        if (slot < 0 || slot >= 3) {
            throw new IllegalArgumentException("Ring slot must be 0, 1, or 2");
        }
        equippedRings[slot] = null;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Ring[] getEquippedRings() {
        return equippedRings.clone(); // Return copy to prevent external modification
    }

    public boolean hasEmptyRingSlot() {
        for (Ring ring : equippedRings) {
            if (ring == null) {
                return true;
            }
        }
        return false;
    }

    public int getFirstEmptyRingSlot() {
        for (int i = 0; i < 3; i++) {
            if (equippedRings[i] == null) {
                return i;
            }
        }
        return -1; // All slots full
    }

    public void reduceHealth(int EnemyAttackStrength) {
        health -= EnemyAttackStrength;
        // Health cannot be negative
        if (health < 0) {
            health = 0;
        }
        // Notify Stats about damage received
        if (game != null && game.getStats() != null) {
            game.getStats().recordDamage(EnemyAttackStrength);
        }
    }

    @Override
    public void update() {
        if (game.isReadyToAttack()){
            attack(); // Sets justAttacked = true internally
        } else if (justAttacked) {
            resetAttack();
        }
        if (game.isStartNewGame()) {
            health = 3000; // Reset to increased health
            resetAttack();
        }
    }

    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }
}
