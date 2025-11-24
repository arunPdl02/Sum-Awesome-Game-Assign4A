package ca.SumAwesomeGame.model.game;

/**
 * Represents the column position of a cell on the game board.
 * Used to determine which enemy character is targeted by an attack.
 * - ONE: Left column (3 cells) -> targets LEFT enemy
 * - TWO: Middle column (2 cells) -> targets MIDDLE enemy
 * - THREE: Right column (3 cells) -> targets RIGHT enemy
 * 
 * @author Sum Awesome Game Team
 */
public enum CellPosition {
    ONE,
    TWO,
    THREE
}
