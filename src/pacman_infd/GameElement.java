/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;


/**
 *
 * @author ivanweller
 */
public abstract class GameElement {
    
    private Cell cell;
    protected GameEventListener gameEventListener;
    
    protected int size;
    protected int x;
    protected int y;
    
    public GameElement(Cell cell, GameEventListener gameEventListener)
    {
        this.cell = cell;
        this.gameEventListener = gameEventListener;
        cell.addElement(this);
        
        size = cell.getSize();
        updatePosition();
    }
    
    /**
     * Update the x and y variables used for drawing this element.
     * This is necessary when a GameElement relocates to a different cell 
     * to draw itself at the correct position.
     */
    protected void updatePosition()
    {
        x = cell.getXpos() * size;
        y = cell.getYPos() * size;
    }

    public abstract void draw(Graphics g);
    
    /**
     * returns the cell containing this GameElement.
     * @return 
     */
    public Cell getCell()
    {
        return cell;
    }
    
    public void setCell(Cell cell)
    {
        this.cell = cell;
    }
 
}
