/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import pacman_infd.Elements.Pacman;

/**
 *
 * @author Marinus
 */
public class PathFinder {

    Cell rootCell;

    public PathFinder() {

    }
    
//    public void findPacman(){
//        List<Cell> path = findPathToPacman(rootCell);
//        
//        for(Cell cell : path){
//            Pacman p = new Pacman(cell, null, 0);
//        }
//          
//    }
    
    public Cell nextCellInPathToPacman(Cell rootCell)
    {
        List<Cell> path = findPathToPacman(rootCell);
        if(path != null && !path.isEmpty()){
            return path.get(0);
        }
        else{
            return rootCell;
        }           
    }
    
    private List contructPath(Cell cell){
    
        LinkedList path = new LinkedList();
        while(cell.pathParent != null){
            path.addFirst(cell);
            cell = cell.pathParent;
        }
        
        return path;
    }
    
    private List findPathToPacman(Cell startCell){

        LinkedList visitedCells = new LinkedList();
        
        Queue queue = new LinkedList();
        queue.offer(startCell);
        startCell.pathParent = null;
        
        while(!queue.isEmpty()){
            Cell cell = (Cell)queue.poll();
            
            for(GameElement e : cell.getElements()){
                if(e instanceof Pacman){
                    //pacman found
                    return contructPath(cell);
                }
            }
            
            //if pacman not found
            visitedCells.add(cell);
            
            for (Object obj : cell.getNeighbors().values()) {
                Cell cellChild = (Cell) obj;
                if(!cellChild.hasWall() && !visitedCells.contains(cellChild) && !queue.contains(cellChild)){
                    cellChild.pathParent = cell;
                    queue.add(cellChild);
                }
                
            }
        }
        
        //no path found
        return null;
    }
    
}
