package ca.SumAwesomeGame.model.equipment.rings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RingsManager {
    private final List<Ring> listOfRings = Arrays.asList(Ring.values());
    // is set by observing stats class
    private List<Ring> activeRings = new ArrayList<>();

    public List<Ring> getActiveRings() {
        return activeRings;
    }
}
