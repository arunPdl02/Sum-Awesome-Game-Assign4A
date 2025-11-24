package ca.SumAwesomeGame.model.util;

import java.util.Random;

/**
 * Utility class for mathematical operations used in the game.
 * Provides random number generation, prime checking, and power-of-two checking.
 * 
 * @author Sum Awesome Game Team
 */
public class GameMath {
    static Random random = new Random();

    public static int getRandomValueBetween(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Checks if a number is prime
     * @param n The number to check
     * @return true if n is prime, false otherwise
     */
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Checks if a number is a power of 2
     * @param n The number to check
     * @return true if n is a power of 2, false otherwise
     */
    public static boolean isPowerOfTwo(int n) {
        if (n < 1) return false;
        return (n & (n - 1)) == 0;
    }
}
