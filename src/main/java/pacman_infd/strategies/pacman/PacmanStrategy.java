package pacman_infd.strategies.pacman;

import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;

import java.awt.event.KeyEvent;

/**
 * Interface for the different pacman strategies.
 */
public interface PacmanStrategy {
    /**
     * Returns the next direction to follow
     *
     * @param currentCell       The current cell
     * @param currentDirection  The current direction
     * @return The new direction
     */
    Direction getNextDirection(Cell currentCell, Direction currentDirection);

    /**
     * Handle the pressure of a directional button.
     *
     * @param e                 The KeyEvent
     * @param currentDirection  The current direction
     * @return The new direction
     */
    Direction changeDirection(KeyEvent e, Direction currentDirection);
}
