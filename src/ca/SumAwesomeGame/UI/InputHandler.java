package ca.SumAwesomeGame.UI;

import java.util.Scanner;

/**
 * Handles user input from the console.
 * Provides a centralized way to read user commands.
 * 
 * @author Sum Awesome Game Team
 */
public class InputHandler {
    static Scanner sc = new Scanner(System.in);

    public static String getInput(){
        System.out.print("Enter a sum: ");
        return sc.nextLine();
    }
}
