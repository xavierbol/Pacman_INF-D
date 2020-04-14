package pacman_infd.strategies.pacman;

import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;

import java.awt.event.KeyEvent;

public class KeyControlledStrategy implements PacmanStrategy {

    private static final String NAME = "Key controlled";

    @Override
    public Cell getNextCell(Cell currentCell, Direction currentDirection) {
        return currentCell.getNeighbor(currentDirection);
    }

    @Override
    public Direction getNewDirection(KeyEvent e, Direction currentDirection) {
        return Direction.getDirection(e);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
