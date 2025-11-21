package ca.SumAwesomeGame.model.equipment.rings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RingsManager {
    private final List<Ring> listOfRings = Arrays.asList(Ring.values());
    private List<Ring> activeRings = new ArrayList<>(); // is set by observing stats class

    public List<Ring> getActiveRings() {
        return activeRings;
    }
}
