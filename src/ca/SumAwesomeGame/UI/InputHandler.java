package ca.SumAwesomeGame.UI;

import ca.SumAwesomeGame.model.equipment.rings.Ring;

import java.util.List;
import java.util.Scanner;

/**
 * Handles user input from the console.
 * Provides a centralized way to read user commands.
 *
 * @author Sum Awesome Game Team
 */
public class InputHandler {
    static Scanner sc = new Scanner(System.in);

    public static String getInput() {
        System.out.print("Enter a sum: ");
        return sc.nextLine();
    }

    public static int getRingInput(List<Ring> rings) {
        System.out.println("Which of the following Rings do you want to replace? enter 1 2 or 3");
        for (Ring ring : rings) {
            System.out.println(ring.getName());
        }
        while (true) {
            System.out.print("> ");
            int input = sc.nextInt();
            if (input >= 1 && input < 4) {
                return (sc.nextInt() - 1);
            }
            System.out.println("Enter a valid input between 1 and 3");
        }
    }
}
