/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marinus
 */
public class Cell {
    
    private static final int DIRECTIONS = 4;
    private int xPos;
    private int yPos;
    private int size;
    
    private Map<Direction, Cell> neighbors;
    private ArrayList<GameElement> elements;
        
    public Cell(int x, int y, int size)
    {
        xPos = x;
        yPos = y;
        this.size = size;
        
        elements = new ArrayList<>();
        neighbors = new HashMap<Direction, Cell>();
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(xPos * size, yPos * size, size, size);
        drawElements(g);
    }
    
    /**
     * Draw all elements contained by this cell.
     * @param g Graphics object
     */
    public void drawElements(Graphics g)
    {
        if(elements != null)
        {
            for(GameElement element : elements)
            {
                element.draw(g);
            }
        }
    }
    
    /**
     * Add a GameElement to this cell.
     * @param e GameElement
     */
    public void addElement(GameElement e)
    {
        elements.add(e);
    }
    
    /**
     * Remove a GameElement from this cell.
     * @param e GameElement
     */
    public void removeElement(GameElement e)
    {
        if(elements.contains(e))
        {
            elements.remove(e);
        }
    }
    
    /**
     * Set the neighboring cells for this cell
     * @param neighbours List of neighbor cells.
     */
    public void setNeighbor(Direction dir, Cell cell)
    {
        neighbors.put(dir, cell);
    }
    
    /**
     * 
     * @return a HashMap of neighboring Cells.
     */
    public Map getNeighbors()
    {
        return neighbors;
    }
    
    /**
     * 
     * @return the size of this cell.
     */
    public int getSize()
    {
        return size;
    }
    
    /**
     * 
     * @return the x position of this cell.
     */
    public int getXpos()
    {
        return xPos;
    }
    
    /**
     * 
     * @return the y position of this cell.
     */
    public int getYPos()
    {
        return yPos;
    }
    
    public String toString()
    {
        return "xPos: " + xPos + "\nYPos: " + yPos + "\nNumber of Neighbors: " + neighbors.size();
    }
}
