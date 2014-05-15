/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Marinus
 */
public class Cell {
    
    private int xPos;
    private int yPos;
    private ArrayList<Cell> neighbours;
    
    private int size;
    
    public Cell(int x, int y, int size)
    {
        xPos = x;
        yPos = y;
        this.size = size;
    }
    
    public void draw(Graphics g)
    {
        g.drawRect(xPos * size, yPos * size, size, size);
    }
}
