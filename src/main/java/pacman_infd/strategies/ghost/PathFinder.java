/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.strategies.ghost;

import pacman_infd.elements.GameElement;
import pacman_infd.elements.Pacman;
import pacman_infd.enums.Direction;
import pacman_infd.games.Cell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Marinus
 */
public class PathFinder {
    public PathFinder() {
    }

    /**
     * The first cell in the path List is the one that the object moving towards
     * pacman needs to take, so this returns the first cell in the path.
     *
     * @param rootCell the root cell where we must find the next cell to reach quickly Pacman.
     * @return first cell in the path towards pacman.
     */
    public Cell nextCellInPathToPacman(Cell rootCell) {
        List<Cell> path = this.breathFirstSearch(rootCell, Pacman.class);
        if (path != null && !path.isEmpty()) {
            return path.get(0);
        } else {
            return null;
        }
    }

    /**
     * Constructs a path (List of cells in order) by 'walking' back along
     * cellParents of each cell starting with the given cell.
     *
     * @param cell start cell from which to walk back.
     * @return list of cells making up the path.
     */
    private List<Cell> contructPath(Cell cell) {
        LinkedList<Cell> path = new LinkedList<>();
        while (cell.getPathParent() != null) {
            path.addFirst(cell);
            cell = cell.getPathParent();
        }

        return path;
    }

    /**
     * Performs a breath first search of the targetCell from the rootCell and return the path found or null if no
     * path was found.
     *
     * @param rootCell   The root cell
     * @param targetCell The target cell
     * @return The path found or null if no path was found
     */
    public List<Cell> breathFirstSearch(Cell rootCell, Cell targetCell) {
        LinkedList<Cell> visitedCells = new LinkedList<>();

        Queue<Cell> queue = new LinkedList<Cell>();
        queue.offer(rootCell);
        rootCell.setPathParent(null);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            if (cell.equals(targetCell)) {
                return contructPath(cell);
            }
            this.handleCell(cell, visitedCells, queue);
        }
        return null;
    }

    /**
     * Performs a breath first search of the GameElement from the rootCell and return the path found or null if no
     * path was found.
     * The search stops as soon as an object of the requested class has been found.
     *
     * @param rootCell    The root cell
     * @param targetClass The class of the GameElement we are looking for
     * @return The path found or null if no path was found
     */
    public List<Cell> breathFirstSearch(Cell rootCell, Class<? extends GameElement> targetClass) {
        LinkedList<Cell> visitedCells = new LinkedList<>();

        Queue<Cell> queue = new LinkedList<Cell>();
        queue.offer(rootCell);
        rootCell.setPathParent(null);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (GameElement e : cell.getAllGameElements()) {
                if (targetClass.isInstance(e)) {
                    return contructPath(cell);
                }
            }
            this.handleCell(cell, visitedCells, queue);
        }
        return null;
    }

    /**
     * Returns the paths from the current cell to the next intersections.
     *
     * @param currentCell The current cell
     * @return The paths from the current cell to the next intersections.
     */
    public List<List<Cell>> getPathsToIntersections(Cell currentCell) {
        LinkedList<Cell> visitedCells = new LinkedList<>();

        Queue<Cell> queue = new LinkedList<Cell>();
        currentCell.setPathParent(null);

        List<List<Cell>> allPaths = new ArrayList<>();

        for (Direction searchDirection : this.getPossibleDirections(currentCell)) {
            Cell cellChild = currentCell.getNeighbor(searchDirection);
            cellChild.setPathParent(currentCell);
            queue.add(cellChild);
        }
        visitedCells.add(currentCell);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            // crossroads = end of path
            if (getPossibleDirections(cell).size() > 2) {
                allPaths.add(contructPath(cell));
            } else {
                this.handleCell(cell, visitedCells, queue);
            }
        }
        return allPaths;
    }

    /**
     * Adds the cell to the visitedCells and, for each neighboring cell, checks if it can be added to the queue and if
     * it can be added does it.
     * A cell can be added to the queue if it is not already in the queue, it has not been visited and it is not a wall.
     *
     * @param cell         The cell to handle
     * @param visitedCells The visited cells
     * @param queue        The queue
     */
    private void handleCell(Cell cell, List<Cell> visitedCells, Queue<Cell> queue) {
        visitedCells.add(cell);

        for (Cell cellChild : cell.getNeighbors().values()) {
            if (!cellChild.hasWall() && !visitedCells.contains(cellChild) && !queue.contains(cellChild)) {
                cellChild.setPathParent(cell);
                queue.add(cellChild);
            }
        }
    }

    /**
     * Returns the list of directions that we can follow from the cell.
     *
     * @param cell The cell
     * @return The list of directions that we can follow from the cell
     */
    public List<Direction> getPossibleDirections(Cell cell) {
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        for (Direction d : Direction.values()) {
            if (!cell.getNeighbor(d).hasWall()) {
                possibleDirections.add(d);
            }
        }
        return possibleDirections;
    }
}
