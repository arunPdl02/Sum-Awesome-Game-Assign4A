package ca.SumAwesomeGame.model.equipment.rings;

import ca.SumAwesomeGame.model.util.GameMath;

import java.util.ArrayList;
import java.util.List;

public class RingsManager {
    private final List<Ring> listOfRings = new ArrayList<>();
    public static final int NUMBER_OF_RINGS = 6;

    public RingsManager() {
        listOfRings.add(new THE_BIG_ONE());
    }

    public Ring getRandomRing() {
        return listOfRings.get(
                GameMath.getRandomValueBetween(0, NUMBER_OF_RINGS)
        );
    }

    public Ring getRingByName(RingsEnum name){
        return listOfRings.stream()
                .filter(r -> r.getName() == name)
                .findFirst()
                .orElse(new NoRing());
    }
}

/**
 * THE_BIG_ONE,
 *     THE_LITTLE_ONE,
 *     RING_OF_TEN_ACITY,
 *     RING_OF_MEH,
 *     THE_PRIME_DIRECTIVE,
 *     THE_TWO_RING
 */