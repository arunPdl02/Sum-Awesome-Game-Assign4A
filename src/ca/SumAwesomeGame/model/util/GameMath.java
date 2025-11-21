package ca.SumAwesomeGame.model.util;

import java.util.Random;

public class GameMath {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 15;
    static Random random = new Random();

    public static int getRandomValueBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
