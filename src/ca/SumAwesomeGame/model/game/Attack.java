package ca.SumAwesomeGame.model.game;

public class Attack {
    private int attackStrength;

    public Attack(int fill) {
        System.out.println("Player attacked!");
        attackStrength = fill;
    }

    public int getAttackStrength(){
        return attackStrength;
    }
}
