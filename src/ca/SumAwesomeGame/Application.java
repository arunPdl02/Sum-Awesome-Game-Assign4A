package ca.SumAwesomeGame;

import ca.SumAwesomeGame.UI.MainUI;
import ca.SumAwesomeGame.model.game.Game;

public class Application {

    public static void main(String[] args){
        Game game = new Game();
        MainUI myUI = new MainUI(game);
        myUI.run();
    }
}
