/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Strategies;

import java.util.ArrayList;
import pacman_infd.Cell;
import pacman_infd.Direction;
import pacman_infd.Elements.Pacman;
import pacman_infd.GameElement;
import pacman_infd.Strategy;

/**
 *
 * @author Marinus
 */
public class FleeStrategy_v0_obsolete implements Strategy {

    private static ArrayList<Cell> checkedCells = new ArrayList<>();
    private Cell lastCell;
    private ArrayList<Direction> bestDirections;
    
    @Override
    public Cell giveNextCell(Cell currentCell) {
        
        Cell pacCell = findPacman(currentCell); 
        Cell nextCell = currentCell;
        
        bestDirections = new ArrayList<>();
        if(pacCell != null){ 
            checkedCells = new ArrayList<>();

            int dx = currentCell.getXpos() - pacCell.getXpos();
            int dy = currentCell.getYPos() - pacCell.getYPos();
            
            if(dx < dy){
                if(dx < 0) {
                    bestDirections.add(Direction.LEFT);
                    if(dy < 0){
                        bestDirections.add(Direction.UP);
                        bestDirections.add(Direction.DOWN);
                    }
                    else{
                        bestDirections.add(Direction.DOWN);
                        bestDirections.add(Direction.UP);
                    }
                    bestDirections.add(Direction.RIGHT);
                }
                else{
                    bestDirections.add(Direction.RIGHT);
                    if(dy < 0){
                        bestDirections.add(Direction.UP);
                        bestDirections.add(Direction.DOWN);
                    }
                    else{
                        bestDirections.add(Direction.DOWN);
                        bestDirections.add(Direction.UP);
                    }
                    bestDirections.add(Direction.LEFT);
                }
            }
            else{
                if(dy < 0){
                    bestDirections.add(Direction.UP);
                    if(dx > 0){
                        bestDirections.add(Direction.RIGHT);
                        bestDirections.add(Direction.LEFT);
                    }
                    else{
                        bestDirections.add(Direction.LEFT);
                        bestDirections.add(Direction.RIGHT);
                    }
                    bestDirections.add(Direction.DOWN);
                }
                else {
                    bestDirections.add(Direction.DOWN);
                    if(dx > 0){
                        bestDirections.add(Direction.RIGHT);
                        bestDirections.add(Direction.LEFT);
                    }
                    else{
                        bestDirections.add(Direction.LEFT);
                        bestDirections.add(Direction.RIGHT);
                    }
                    bestDirections.add(Direction.UP);
                }
            }
        }
        
        for(Direction d : bestDirections)
        {
            if(!currentCell.getNeighbor(d).hasWall() && currentCell.getNeighbor(d) != lastCell){
                nextCell = currentCell.getNeighbor(d);
                break;
            }

        }
        lastCell = currentCell;
        return nextCell;
    }
    
    private Cell findPacman(Cell cell)
    {
        for(GameElement e : cell.getElements()){
            if(e instanceof Pacman){
                System.out.println("Pacman gevonden!");
                return cell;
            }
        }
        checkedCells.add(cell);
        lastCell = cell;

        
        for(Object o : cell.getNeighbors().values().toArray())
        {
            Cell c = (Cell)o;
            if(!checkedCells.contains(c)){
                return findPacman(c);
            }
            
        }
        
        System.out.println("Kan pacman niet vinden");
        return null;
    }
    
}
