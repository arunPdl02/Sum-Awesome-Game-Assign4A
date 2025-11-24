package ca.SumAwesomeGame.model.equipment;

import ca.SumAwesomeGame.model.equipment.rings.*;
import ca.SumAwesomeGame.model.equipment.weapons.*;
import ca.SumAwesomeGame.model.util.GameMath;

/**
 * Factory for creating weapons and rings.
 * Provides methods to create equipment by ID or randomly.
 */
public class EquipmentFactory {
    // Weapon IDs: 1-6
    public static final int LIGHTNING_WAND_ID = 1;
    public static final int FIRE_STAFF_ID = 2;
    public static final int FROST_BOW_ID = 3;
    public static final int STONE_HAMMER_ID = 4;
    public static final int DIAMOND_SWORD_ID = 5;
    public static final int SPARKLE_DAGGER_ID = 6;
    
    // Ring IDs: 1-6
    public static final int THE_BIG_ONE_ID = 1;
    public static final int THE_LITTLE_ONE_ID = 2;
    public static final int RING_OF_TENACITY_ID = 3;
    public static final int RING_OF_MEH_ID = 4;
    public static final int THE_PRIME_DIRECTIVE_ID = 5;
    public static final int THE_TWO_RING_ID = 6;

    /**
     * Creates a weapon by ID
     * @param id Weapon ID (1-6)
     * @return Weapon instance, or null if invalid ID
     */
    public static Weapon createWeapon(int id) {
        return switch (id) {
            case LIGHTNING_WAND_ID -> new LightningWand();
            case FIRE_STAFF_ID -> new FireStaff();
            case FROST_BOW_ID -> new FrostBow();
            case STONE_HAMMER_ID -> new StoneHammer();
            case DIAMOND_SWORD_ID -> new DiamondSword();
            case SPARKLE_DAGGER_ID -> new SparkleDagger();
            default -> null;
        };
    }

    /**
     * Creates a ring by ID
     * @param id Ring ID (1-6)
     * @return Ring instance, or null if invalid ID
     */
    public static Ring createRing(int id) {
        return switch (id) {
            case THE_BIG_ONE_ID -> new TheBigOne();
            case THE_LITTLE_ONE_ID -> new TheLittleOne();
            case RING_OF_TENACITY_ID -> new RingOfTenacity();
            case RING_OF_MEH_ID -> new RingOfMeh();
            case THE_PRIME_DIRECTIVE_ID -> new ThePrimeDirective();
            case THE_TWO_RING_ID -> new TheTwoRing();
            default -> null;
        };
    }

    /**
     * Gets a random weapon
     * @return Random weapon (1-6)
     */
    public static Weapon getRandomWeapon() {
        int randomId = GameMath.getRandomValueBetween(1, 6);
        return createWeapon(randomId);
    }

    /**
     * Gets a random ring
     * @return Random ring (1-6)
     */
    public static Ring getRandomRing() {
        int randomId = GameMath.getRandomValueBetween(1, 6);
        return createRing(randomId);
    }

    /**
     * Gets a random equipment (weapon or ring)
     * @return Random equipment (either Weapon or Ring)
     */
    public static Object getRandomEquipment() {
        // Randomly choose weapon or ring
        boolean isWeapon = GameMath.getRandomValueBetween(0, 1) == 0;
        return isWeapon ? getRandomWeapon() : getRandomRing();
    }
}

