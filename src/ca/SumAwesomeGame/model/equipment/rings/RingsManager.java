package ca.SumAwesomeGame.model.equipment.rings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RingsManager {
    private final List<Ring> listOfRings = new ArrayList<>();

    public RingsManager() {
        listOfRings.add(new THE_BIG_ONE());
    }

    public Ring gerRingByName(){
        return new THE_BIG_ONE();
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