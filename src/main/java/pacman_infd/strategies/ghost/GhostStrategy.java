/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.strategies.ghost;

import pacman_infd.games.Cell;

/**
 *
 * @author Marinus
 */
public interface GhostStrategy {
    /**
     * Give the next cell that the ghost will go.
     *
     * @param currentCell the current cell where the ghost is.
     * @return the next cell where the ghost will go.
     */
    Cell giveNextCell(Cell currentCell);
}
