/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.util.ArrayList;

/**
 *
 * @author Marinus
 */
public class GameWorld {
    
    private static final int CELL_SIZE = 25; //pixels
    private static final int WIDTH = 10; //cells
    private static final int HEIGHT = 10; //cells
    
    private ArrayList<Cell> cells;
    
    public GameWorld()
    {
        createCells();
    }
    
    private void createCells()
    {
        cells = new ArrayList<>();
        
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Cell cell = new Cell(x, y, CELL_SIZE);
                cells.add(cell);
            }
        }
    }
}
