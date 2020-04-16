package pacman_infd.strategies.pacman;

import pacman_infd.elements.Ghost;
import pacman_infd.enums.Direction;
import pacman_infd.enums.GhostState;
import pacman_infd.games.Cell;
import pacman_infd.games.GameWorld;
import pacman_infd.strategies.ghost.PathFinder;

import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Artificial intelligence strategy for pacman giving priority to the score.
 * The goal is to finish the level without dying with the highest possible score.
 */
public class PriorityToScoreStrategy implements PacmanStrategy {
    private GameWorld gameWorld;
    private PathFinder pathFinder;

    /**
     * Constructor of the class
     *
     * @param gameWorld The current gameWorld
     */
    public PriorityToScoreStrategy(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.pathFinder = new PathFinder();
    }

    @Override
    public Direction getNextDirection(Cell currentCell, Direction currentDirection) {
        List<Direction> possibleDirections = pathFinder.getPossibleDirections(currentCell);
        Collections.shuffle(possibleDirections);

        if (possibleDirections.size() == 1) {
            return possibleDirections.get(0);
        } else if (possibleDirections.size() == 2) {
            if (currentDirection != null) {
                possibleDirections.remove(currentDirection.getOpposite());
            }
            return possibleDirections.get(0);
        }

        List<Cell> pathToNearestGhost = pathFinder.breathFirstSearch(currentCell, Ghost.class);
        if (pathToNearestGhost.size() != 0) {
            Ghost ghost = pathToNearestGhost.get(pathToNearestGhost.size() - 1).getMovingElements().stream()
                    .filter(movingGameElement -> movingGameElement instanceof Ghost)
                    .map(movingGameElement -> (Ghost) movingGameElement)
                    .findAny()
                    .orElse(null);
            if (ghost.getState() == GhostState.VULNERABLE && !ghost.isEndingVulnerability()) {
                return PacmanAI.getDirectionOfCell(currentCell, pathToNearestGhost.get(0));
            }
        }

        List<Cell> ghostCells = this.gameWorld.getGhostCells();
        List<List<Cell>> allPaths = pathFinder.getPathsToIntersections(currentCell);
        List<List<Cell>> unblockedPaths = PacmanAI.getUnblockedPaths(ghostCells, allPaths);

        if (unblockedPaths.isEmpty()) {
            return null;
        } else if (unblockedPaths.size() == 1) {
            return PacmanAI.getDirectionOfCell(currentCell, unblockedPaths.get(0).get(0));
        }

        List<List<Cell>> safePaths = PacmanAI.getSafePaths(currentCell, ghostCells, unblockedPaths);

        // Add risky paths that are worthwhile to safePaths.
        Set<List<Cell>> setSafePaths = new HashSet<List<Cell>>(safePaths);
        Set<List<Cell>> setDangerousPaths = new HashSet<List<Cell>>(unblockedPaths);
        setDangerousPaths.removeAll(setSafePaths);

        safePaths.addAll(PacmanAI.getWorthItPaths(currentCell, ghostCells, new ArrayList<>(setDangerousPaths)));

        if (safePaths.isEmpty()) {
            return PacmanAI.fleeNearestGhost(currentCell, possibleDirections);
        } else if (safePaths.size() == 1) {
            return PacmanAI.getDirectionOfCell(currentCell, safePaths.get(0).get(0));
        } else {
            return PacmanAI.maximizePoints(currentCell, safePaths);
        }
    }

    @Override
    public Direction changeDirection(KeyEvent e, Direction currentDirection) {
        return currentDirection;
    }
}
