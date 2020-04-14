package pacman_infd.strategies.pacman;

import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;

import java.awt.event.KeyEvent;

public interface PacmanStrategy {
    Cell getNextCell(Cell currentCell, Direction currentDirection);
    Direction getNewDirection(KeyEvent e, Direction currentDirection);
    String getName();
}
