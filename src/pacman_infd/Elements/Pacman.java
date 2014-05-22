/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_infd.Elements;

import java.awt.Color;
import java.awt.Graphics;
import pacman_infd.Cell;
import pacman_infd.Direction;
import pacman_infd.GameElement;

/**
 *
 * @author ivanweller
 */
public class Pacman extends GameElement{

    public Pacman(Cell cell) {
        super(cell);
    }

    public void move(Direction direction) {
        Cell moveTo = getCell().getNeighbor(direction);
        if(moveTo != null && !moveTo.hasWall())
        {
            moveTo.removePellet();
            moveTo.addElement(this);
            getCell().removeElement(this);
            setCell(moveTo);
        }
        
    }
    

    public void draw(Graphics g) {
        int size = getCell().getSize();
        int x = getCell().getXpos() * size;
        int y = getCell().getYPos() * size;

        g.setColor(Color.ORANGE);
        g.fillOval(x, y, size, size);
    }

    
}
