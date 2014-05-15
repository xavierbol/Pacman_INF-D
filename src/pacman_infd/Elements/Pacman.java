/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pacman_infd.Cell;
import pacman_infd.GameElement;

/**
 *
 * @author ivanweller
 */
public class Pacman extends GameElement{
    
    public Pacman(Cell cell)
    {
        super(cell);
    }
    
    public void move(int direction){
        
    }
    
    public int getDirectionFromKeyListener(){
        return 0;
        
    }
    
    public void draw(Graphics g)
    {
        int size = getCell().getSize();
        int x = getCell().getXpos() * size;
        int y = getCell().getYPos() * size;

        g.setColor(Color.ORANGE);
        g.fillOval(x, y, size, size);
    }
 
}
