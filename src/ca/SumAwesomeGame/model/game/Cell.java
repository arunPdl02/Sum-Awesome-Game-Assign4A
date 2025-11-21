package ca.SumAwesomeGame.model.game;

public class Cell {
    private long value;
    private boolean cellLocked = true;

    public long getValue() {
        return value;
    }

    public Cell(long value) {
        this.value = value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean isCellLocked(){
        return cellLocked;
    }

    public void unlockCell(){
        cellLocked = false;
    }

    public String toString(){
        if(cellLocked){
            return "  " + value + "  ";
        }
        return " _" + value + "_ ";
    }

}
