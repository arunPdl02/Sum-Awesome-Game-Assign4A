package ca.SumAwesomeGame.model.fill;

public class Fill {
    private int strength;

    public Fill() {
        this.strength = 0;
    }

    public int getStrength() {
        return strength;
    }

    public void increaseStrength(int strengthIncrease){
        strength += strengthIncrease;
    }
}
