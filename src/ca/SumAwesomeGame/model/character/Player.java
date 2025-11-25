package ca.SumAwesomeGame.model.character;

import ca.SumAwesomeGame.model.equipment.rings.Ring;
import ca.SumAwesomeGame.model.equipment.weapons.NullWeapon;
import ca.SumAwesomeGame.model.equipment.weapons.Weapon;
import ca.SumAwesomeGame.model.equipment.weapons.WeaponsManager;
import ca.SumAwesomeGame.model.game.Attack;
import ca.SumAwesomeGame.model.game.AttackResult;
import ca.SumAwesomeGame.model.game.AttackTarget;
import ca.SumAwesomeGame.model.game.Game;
import ca.SumAwesomeGame.model.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player character in the game.
 * Manages player health, equipment (weapon and rings), and attack behavior.
 * Implements GameObserver to respond to game state changes.
 *
 * @author Sum Awesome Game Team
 */
public class Player implements GameObserver {
    private Game game;
    private Weapon equippedWeapon = new NullWeapon();
    private List<Ring> equippedRings = new ArrayList<>(); // null = empty slot
    public boolean justAttacked = false;
    private AttackResult lastAttackResult = null; // Store last attack result for UI display

    private int health = 3000; // Increased for easier testing

    /**
     * Constructs a new Player with default health and no equipment.
     */
    public Player() {
    }

    /**
     * Resets the attack state after an attack has been processed.
     */
    public void resetAttack() {
        justAttacked = false;
        lastAttackResult = null; // Clear attack result after display
    }

    /**
     * Gets the current health of the player.
     *
     * @return Current health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Checks if the player is dead (health <= 0).
     *
     * @return true if player is dead, false otherwise
     */
    public boolean isDead() {
        return health <= 0;
    }

    private void attack() {
        // Create attack with equipment integration
        equippedWeapon = new WeaponsManager().getActiveWeapon(game.getFillObject());
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

    /**
     * Gets the result of the last attack performed by the player.
     *
     * @return AttackResult of the last attack, or null if no attack has occurred
     */
    public AttackResult getLastAttackResult() {
        return lastAttackResult;
    }

    // ========== Equipment Management Methods ==========

    /**
     * Equips a weapon to the player.
     *
     * @param weapon The weapon to equip
     */
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    /**
     * Unequips the current weapon (replaces with NullWeapon).
     */
    public void unequipWeapon() {
        equippedWeapon = new NullWeapon();
    }

    /**
     * Equips a ring to a specific slot (0, 1, or 2).
     *
     * @param ring The ring to equip
     * @param slot The slot index (0, 1, or 2)
     * @throws IllegalArgumentException if slot is out of range
     */
    public void equipRing(Ring ring, int slot) {
        if (slot < 0 || slot >= 3) {
            throw new IllegalArgumentException("Ring slot must be 0, 1, or 2");
        }
        equippedRings.add(ring);
    }

    /**
     * Gets the currently equipped weapon.
     *
     * @return The equipped weapon (may be NullWeapon if none equipped)
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * Gets a copy of the array of equipped rings.
     *
     * @return List of the equipped rings array
     */
    public List<Ring> getEquippedRings() {
        return new ArrayList<>(equippedRings); // Return copy to prevent external modification
    }

    /**
     * Gets the index of the first empty ring slot.
     *
     * @return Index of first empty slot (0, 1, or 2), or -1 if all slots are full
     */
    public boolean canPlayerEquipMoreRings() {
        return equippedRings.size() < 3;
    }

    /**
     * Reduces the player's health by the specified amount.
     * Health cannot go below 0. Also records damage in the stats tracker.
     *
     * @param EnemyAttackStrength The amount of damage to take
     */
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

    /**
     * Updates the player state based on game events.
     * Performs attack if ready, resets attack state after processing, and resets health on new game.
     */
    @Override
    public void update() {
        if (game.isReadyToAttack()) {
            attack(); // Sets justAttacked = true internally
        } else if (justAttacked) {
            resetAttack();
        }
        if (game.isStartNewGame()) {
            health = 3000; // Reset to increased health
            resetAttack();
        }
    }

    /**
     * Subscribes the player to game state changes.
     *
     * @param game The game instance to observe
     */
    @Override
    public void listenToGame(Game game) {
        this.game = game;
        game.subscribe(this);
    }

    public void removeRingAtIndex(int index) {
        if (index < 3) {
            equippedRings.remove(index);
        }
    }

    public void addRing(Ring ring) {
        equippedRings.add(ring);
    }
}
