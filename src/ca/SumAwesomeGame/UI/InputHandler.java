package ca.SumAwesomeGame.UI;

import java.util.Scanner;

public class InputHandler {
    static Scanner sc = new Scanner(System.in);

    public static String getInput(){
        System.out.print("> ");
        return sc.nextLine();
    }
}
