package ca.SumAwesomeGame;

import ca.SumAwesomeGame.UI.MainUI;

/**
 * Main entry point for the Sum Awesome game application.
 * Initializes and runs the game UI.
 * 
 * @author Sum Awesome Game Team
 */
public class Application {

    public static void main(String[] args){
        MainUI myUI = new MainUI();
        myUI.run();
    }
}
