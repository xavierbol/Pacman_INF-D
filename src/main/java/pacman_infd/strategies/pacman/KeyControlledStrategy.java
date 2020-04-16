package pacman_infd.strategies.pacman;

import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;
import pacman_infd.games.GameWorld;

import java.awt.event.KeyEvent;

/**
 * Strategy where pacman is controlled by the directional keys of the keyboard.
 */
public class KeyControlledStrategy implements PacmanStrategy {
    public KeyControlledStrategy(GameWorld gameWorld) {
    }

    @Override
    public Direction getNextDirection(Cell currentCell, Direction currentDirection) {
        return currentDirection;
    }

    @Override
    public Direction changeDirection(KeyEvent e, Direction currentDirection) {
        return Direction.getDirection(e);
    }
}
