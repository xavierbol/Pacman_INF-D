/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd;

import java.awt.Graphics;
import java.awt.Point;


/**
 *
 * @author ivanweller
 */
public abstract class GameElement {
    
    private Cell cell;
    protected GameEventListener gameEventListener;
    
    public GameElement(Cell cell, GameEventListener gameEventListener)
    {
        this.cell = cell;
        this.gameEventListener = gameEventListener;
        cell.addElement(this);
    }
    
    protected Point getPosition(){
        return new Point(getCell().getXpos() * getCell().getSize(), getCell().getYPos() * getCell().getSize());
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
