/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.strategies.ghost;

import pacman_infd.games.Cell;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Marinus
 */
public class MoveRandomStrategy implements GhostStrategy {
    private Cell previousCell;
    
    @Override
    public Cell giveNextCell(Cell currentCell) {
        // new list of cells
        ArrayList<Cell> availableCells = new ArrayList<>();
        
        // put all neighboring cells that have no wall in the new list.
        for (Object o : currentCell.getNeighbors().values().toArray()) {
            Cell cell = (Cell) o;
            if (!cell.hasWall()) {
                availableCells.add(cell);
            }
        }
        
        // remove the last cell that the element was on from the list so it doesn't
        // move back and forth between the same cells except if it's the only available cell.
        if (availableCells.size() > 1 && previousCell != null) {
            availableCells.remove(previousCell);
        }

        // select a random cell from the remaining list of cells.
        Random r = new Random();
        Cell nextCell = availableCells.get(r.nextInt(availableCells.size()));
        
        // make the current cell the old cell.
        previousCell = currentCell;

        return nextCell;
    }
}
