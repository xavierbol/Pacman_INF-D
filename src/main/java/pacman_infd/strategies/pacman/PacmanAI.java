package pacman_infd.strategies.pacman;

import pacman_infd.elements.Fruit;
import pacman_infd.elements.Ghost;
import pacman_infd.elements.Pellet;
import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;
import pacman_infd.strategies.ghost.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class with basic functions for pacman artificial intelligence.
 */
public final class PacmanAI {

    private final static PathFinder pathFinder = new PathFinder();

    /**
     * Returns the direction to flee the nearest ghost
     *
     * @param currentCell        The current cell
     * @param possibleDirections The directions that pacman can take
     * @return The direction to flee the nearest ghost
     */
    public static Direction fleeNearestGhost(Cell currentCell, List<Direction> possibleDirections) {
        List<Cell> nearestGhostPath = pathFinder.breathFirstSearch(currentCell, Ghost.class);
        if (nearestGhostPath.size() != 0) {
            Direction ghostDirection = getDirectionOfCell(currentCell, nearestGhostPath.get(0));
            if (possibleDirections.contains(ghostDirection.getOpposite())) {
                return ghostDirection.getOpposite();
            } else {
                possibleDirections.remove(ghostDirection);
            }
        }
        return possibleDirections.get(0);
    }

    /**
     * Returns the direction that maximizes the points earned by the pellets and fruits
     *
     * @param currentCell The current cell
     * @param safePaths   The list of paths that are safe
     * @return The direction that maximizes the points earned by the pellets and fruits
     */
    public static Direction maximizePoints(Cell currentCell, List<List<Cell>> safePaths) {
        List<Cell> nearestFruitPath = pathFinder.breathFirstSearch(currentCell, Fruit.class);
        if (nearestFruitPath != null) {
            return PacmanAI.maximizeFruits(currentCell, safePaths, nearestFruitPath);
        } else {
            return PacmanAI.maximizePellets(currentCell, safePaths);
        }
    }

    /**
     * Returns the direction which maximizes the number of pellets
     *
     * @param currentCell The current cell
     * @param safePaths   The list of paths that are safe
     * @return The direction which maximizes the number of pellets.
     */
    public static Direction maximizePellets(Cell currentCell, List<List<Cell>> safePaths) {
        int bestPelletsCount = 0;
        Direction bestDirection = getDirectionOfCell(currentCell, safePaths.get(0).get(0));
        for (List<Cell> path : safePaths) {
            int pelletsCount = 0;
            for (Cell cell : path) {
                if (cell.getAllGameElements().stream().anyMatch(gameElement -> gameElement instanceof Pellet)) {
                    pelletsCount++;
                }
            }
            if (pelletsCount > bestPelletsCount) {
                bestPelletsCount = pelletsCount;
                bestDirection = getDirectionOfCell(currentCell, path.get(0));
            }
        }
        if (bestPelletsCount == 0) {
            List<Cell> pathToNearestPellet = pathFinder.breathFirstSearch(currentCell, Pellet.class);
            for (List<Cell> path : safePaths) {
                if (path.get(0).equals(pathToNearestPellet.get(0))) {
                    return getDirectionOfCell(currentCell, pathToNearestPellet.get(0));
                }
            }
        }
        return bestDirection;
    }

    /**
     * Returns the direction that brings pacman closer to the nearest fruit
     *
     * @param currentCell        The current cell
     * @param safePaths          The list of paths that are safe
     * @param pathToNearestFruit The optimal path to reach the nearest fruit
     * @return The direction that brings pacman closer to the nearest fruit
     */
    public static Direction maximizeFruits(Cell currentCell, List<List<Cell>> safePaths, List<Cell> pathToNearestFruit) {
        Direction fruitDirection = PacmanAI.getDirectionOfCell(currentCell, pathToNearestFruit.get(0));
        List<Direction> safeDirections = safePaths.stream()
                .map(path -> PacmanAI.getDirectionOfCell(currentCell, path.get(0)))
                .collect(Collectors.toList());
        if (safeDirections.contains(fruitDirection)) {
            return fruitDirection;
        } else {
            safeDirections.remove(fruitDirection.getOpposite());
            return safeDirections.get(0);
        }
    }

    /**
     * Returns the list of paths that are not blocked by a ghost
     *
     * @param ghostCells The cells where the ghosts are
     * @param allPaths   All possible paths for pacman
     * @return The list of paths that are not blocked by a ghost
     */
    public static List<List<Cell>> getUnblockedPaths(List<Cell> ghostCells, List<List<Cell>> allPaths) {
        List<List<Cell>> unblockedPaths = new ArrayList<>(allPaths);
        for (List<Cell> path : allPaths) {
            for (Cell ghostCell : ghostCells) {
                if (path.contains(ghostCell)) {
                    unblockedPaths.remove(path);
                    break;
                }
            }
        }
        return unblockedPaths;
    }

    /**
     * Returns the list of paths that are safe.
     * A path is safe if no ghost can reach the end of the path, i.e. the destination, before pacman.
     *
     * @param currentCell    The current cell
     * @param ghostCells     The cells where the ghosts are
     * @param unblockedPaths The list of paths that are not blocked by a ghost
     * @return The list of paths that are safe
     */
    public static List<List<Cell>> getSafePaths(Cell currentCell, List<Cell> ghostCells, List<List<Cell>> unblockedPaths) {
        List<List<Cell>> safePaths = new ArrayList<>(unblockedPaths);

        for (List<Cell> unblockedPath : unblockedPaths) {
            for (Cell ghostCell : ghostCells) {
                List<Cell> ghostPath = pathFinder.breathFirstSearch(ghostCell, unblockedPath.get(unblockedPath.size() - 1));
                if (ghostPath.size() <= unblockedPath.size()) {
                    Ghost ghost = (Ghost) ghostCell.getMovingElements().stream()
                            .filter(movingGameElement -> movingGameElement instanceof Ghost)
                            .findAny().orElse(null);
                    safePaths.remove(unblockedPath);
                    break;
                }
            }
        }
        return safePaths;
    }

    /**
     * Returns the list of dangerous paths that are worth following.
     * Pacman can take the risk of following a dangerous path if the path contains at least one pellet and if all the
     * ghosts are either further away than pacman from the destination or close to him. In the latter case, we hope that
     * the ghosts will follow pacman and that pacman will arrive first at the destination.
     *
     * @param currentCell    The current cell
     * @param ghostCells     The cells where the ghosts are
     * @param dangerousPaths The list of dangerous paths
     * @return The list of dangerous paths that are worth following
     */
    public static List<List<Cell>> getWorthItPaths(Cell currentCell, List<Cell> ghostCells, List<List<Cell>> dangerousPaths) {
        List<List<Cell>> worthItPaths = new ArrayList<>();

        for (List<Cell> dangerousPath : dangerousPaths) {
            boolean canAdd = true;
            Cell destination = dangerousPath.get(dangerousPath.size() - 1);

            for (Cell ghostCell : ghostCells) {
                int ghostDistanceToDestination = pathFinder.breathFirstSearch(ghostCell, destination).size();
                int pacmanDistanceToDestination = dangerousPath.size();
                int ghostDistanceToPacman = pathFinder.breathFirstSearch(ghostCell, currentCell).size();
                if (!(ghostDistanceToPacman < ghostDistanceToDestination || pacmanDistanceToDestination < ghostDistanceToDestination)) {
                    canAdd = false;
                    break;
                }
            }
            if (canAdd && isPelletPath(dangerousPath)) {
                worthItPaths.add(dangerousPath);
            }
        }
        return worthItPaths;
    }

    /**
     * Returns the direction in which the target cell is located relative to the current cell.
     * The target cell must be neighbour to the current cell otherwise the function returns null.
     *
     * @param currentCell The current cell
     * @param targetCell  The target cell
     * @return the direction in which the target cell is located relative to the current cell
     */
    public static Direction getDirectionOfCell(Cell currentCell, Cell targetCell) {
        for (Map.Entry<Direction, Cell> entry : currentCell.getNeighbors().entrySet()) {
            if (entry.getValue().equals(targetCell)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns if the path contains at least one pellet.
     *
     * @param path The path.
     * @return True if the path contains at least one pellet, false otherwise.
     */
    private static boolean isPelletPath(List<Cell> path) {
        for (Cell cell : path) {
            if (cell.getAllGameElements().stream().anyMatch(gameElement -> gameElement instanceof Pellet)) {
                return true;
            }
        }
        return false;
    }
}
