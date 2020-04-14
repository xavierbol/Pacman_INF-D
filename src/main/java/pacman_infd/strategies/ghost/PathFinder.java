/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.strategies.ghost;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import pacman_infd.games.Cell;

/**
 *
 * @author Marinus
 */
public class PathFinder {
    public PathFinder() { }

    /**
     * The first cell in the path List is the one that the object moving towards
     * pacman needs to take, so this returns the first cell in the path.
     *
     * @param rootCell the root cell where we must find the next cell to reach quickly Pacman.
     * @return first cell in the path towards pacman.
     */
    public Cell nextCellInPathToPacman(Cell rootCell) {
        List<Cell> path = findPathToPacman(rootCell);
        return nextCellInPath(path);
    }

    public Cell nextCellInPath(Cell rootCell, Cell targetCell) {
        List<Cell> path = findPathToCell(rootCell, targetCell);
        return nextCellInPath(path);
    }

    private Cell nextCellInPath(List<Cell> path) {
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

    private List<Cell> breathFirstSearch(Cell rootCell, Cell targetCell) {
        LinkedList<Cell> visitedCells = new LinkedList<>();

        Queue<Cell> queue = new LinkedList<Cell>();
        queue.offer(rootCell);
        rootCell.setPathParent(null);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            if (targetCell == null) {
                if (cell.containsPacman()) {
                    return contructPath(cell);
                }
            } else {
                if (cell.equals(targetCell)) {
                    // targetCell found
                    return contructPath(cell);
                }
            }

            visitedCells.add(cell);

            for (Cell cellChild : cell.getNeighbors().values()) {
                if (!cellChild.hasWall() && !visitedCells.contains(cellChild) && !queue.contains(cellChild)) {
                    cellChild.setPathParent(cell);
                    queue.add(cellChild);
                }
            }
        }

        // no path found
        return null;
    }

    /**
     * Uses a Breath-First search algorithm to determine the shortest path from
     * the start cell to the cell containing Pacman.
     *
     * @param startCell the first cell of the map where the algorithm will search Pacman.
     * @return a path to reach Pacman, if the algorithm doesn't find a path, then it returns null.
     */
    public List<Cell> findPathToPacman(Cell startCell) {
        return breathFirstSearch(startCell, null);
    }

    /**
     * Uses a Breath-First search algorithm to determine the shortest path from
     * the start cell to the target cell.
     *
     * @param startCell the first cell of the map where the algorithm will search Pacman.
     * @param targetCell the cell to reach.
     * @return a path or null if the algorithm doesn't find a path.
     */
    private List<Cell> findPathToCell(Cell startCell, Cell targetCell) {
        return breathFirstSearch(startCell, targetCell);
    }
}
