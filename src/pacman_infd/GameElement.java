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
    
    public GameElement(Cell cell)
    {
        this.cell = cell;
        cell.addElement(this);
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
 
}
