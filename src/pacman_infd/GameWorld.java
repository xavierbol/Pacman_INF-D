/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import pacman_infd.Elements.*;

/**
 *
 * @author Marinus
 */
public class GameWorld extends JPanel{
    
    private static final int CELL_SIZE = 25; //pixels
    private static final int WIDTH = 30; //cells
    private static final int HEIGHT = 22; //cells
    
    private ArrayList<Cell> cells;
    private Cell[][] cellMap;
    
    public GameWorld()
    { 
        createCells();
        findNeighbors();

        Pacman pacman = new Pacman(cells.get(0));
        
        neighborTest();

    }
    
    /**
     * Create a grid of cells using the WIDTH and HEIGHT.
     */
    private void createCells()
    {
        cells = new ArrayList<>();
        cellMap = new Cell[WIDTH][HEIGHT];
        
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Cell cell = new Cell(x, y, CELL_SIZE);
                cellMap[x][y] = cell;
                cells.add(cell);
            }
        }
    }
    
    /**
     * Finds all neighbors for each cell and adds them to the neighbors Map of each cell.
     */
    private void findNeighbors()
    {
        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                if(x - 1 >= 0)
                {
                    cellMap[x][y].setNeighbor(Direction.LEFT, cellMap[x-1][y]);
                }
                if(x + 1 < WIDTH)
                {
                    cellMap[x][y].setNeighbor(Direction.RIGHT, cellMap[x+1][y]);
                }
                if(y - 1 >= 0)
                {
                    cellMap[x][y].setNeighbor(Direction.DOWN, cellMap[x][y-1]);
                }
                if(y + 1 < HEIGHT)
                {
                    cellMap[x][y].setNeighbor(Direction.UP, cellMap[x][y+1]);
                }
            }
        }
    }
    
    private void drawCells(Graphics g)
    {
        if(cells != null)
        {
            for(Cell cell : cells)
            {
                cell.draw(g);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        drawCells(g);  
    }
    
    public void neighborTest()
    {
        for(Cell cell : cells)
        {
            System.out.println(cell);
        }
    }
}
