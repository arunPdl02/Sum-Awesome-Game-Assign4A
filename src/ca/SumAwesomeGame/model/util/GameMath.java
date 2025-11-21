package ca.SumAwesomeGame.model.util;

import java.util.Random;

public class GameMath {
    static Random random = new Random();

    public static int getRandomValueBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
